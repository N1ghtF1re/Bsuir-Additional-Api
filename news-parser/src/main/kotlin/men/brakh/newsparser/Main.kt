package men.brakh.newsparser

import com.fasterxml.jackson.databind.ObjectMapper
import men.brakh.newsparser.config.Config
import men.brakh.newsparser.model.ParsersLoader
import men.brakh.newsparser.model.dto.CreateNewsRequest
import men.brakh.newsparser.model.parser.Parser
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.concurrent.timerTask


val parsers:List<Parser> = ParsersLoader.load().map { clazz -> clazz.newInstance() }

val updatesFilePath = "data/lastUpdate.data".toPath()

private fun String.toPath(): Path = Paths.get(this)

fun init() {
    if(!Files.exists(updatesFilePath)) {
        Files.createFile(updatesFilePath)
        Files.write(updatesFilePath, Date().time.toString().toByteArray())
    }
}

fun updateDate() {
    FileOutputStream(updatesFilePath.toFile(), false).use { fileOutputStream: FileOutputStream ->
        fileOutputStream.write(Date().time.toString().toByteArray())
    }
}

fun getLastUpdate() = Date(String(Files.readAllBytes(updatesFilePath)).toLong())
val objectMapper = ObjectMapper()
fun sendRequest(news: CreateNewsRequest) {
    val client = HttpClientBuilder
            .create()
            .build()

    client.use { httpClient ->
        val post = HttpPost("${Config.apiHost}/news")
        post.addHeader("X-Service-User-Authorization", Config.apiToken)
        post.setHeader("Accept", "application/json")
        post.setHeader("Content-type", "application/json; charset=UTF-8")

        post.entity = EntityBuilder.create()
                .setContentType(ContentType.APPLICATION_JSON)
                .setContentEncoding("UTF-8")
                .setText( objectMapper.writeValueAsString(news) )
                .build()

        httpClient.execute(post).use { resp ->
            if(resp.statusLine.statusCode !in 200..201) {
                logger.error("ERROR. Can't send response to add: ${InputStreamReader(resp.entity.content).readText()}")
                logger.error("REQUEST: $news")
            }
        }

    }


}

private val logger = LoggerFactory.getLogger("Main")

fun main() {
    init()

    val timer = Timer()
    timer.scheduleAtFixedRate(
            timerTask {
                val lastUpdate = getLastUpdate()
                val newsList = parsers.flatMap { parser ->
                    parser.parse(lastUpdate)
                }
                logger.info("Updated news list. Added ${newsList.count()} news")
                updateDate()

                newsList.forEach(::sendRequest)
            }, 0, 10 * 1000 * 60

    )

}