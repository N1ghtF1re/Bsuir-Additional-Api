package men.brakh.newsparser

import com.google.gson.Gson
import men.brakh.newsparser.config.Config
import men.brakh.newsparser.model.News
import men.brakh.newsparser.parser.Parser
import men.brakh.newsparser.parser.site.FksisSiteParser
import men.brakh.newsparser.parser.vk.*
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.concurrent.timerTask


val parsers:List<Parser> = listOf(
        FksisSiteParser(),
        FicVKParser(),
        FituVKParser(),
        FksisVKParser(),
        FreVKParser(),
        IefVKParser()
)

val updatesFilePath = "lastUpdate.data".toPath()

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

fun sendRequest(news: News) {
    val client = HttpClientBuilder
            .create()
            .build()

    client.use { httpClient ->
        val post = HttpPost("${Config.apiHost}/news")
        post.addHeader("Authorization", Config.apiToken)
        val entity = StringEntity(Gson().toJson(news))
        post.entity = entity

        httpClient.execute(post)
    }


}

fun main() {
    init()

    val timer = Timer()
    timer.scheduleAtFixedRate(
            timerTask {
                val lastUpdate = getLastUpdate()
                val newsList = parsers.flatMap { parser ->
                    parser.parse(lastUpdate)
                }
                println(newsList)
                updateDate()

                newsList.forEach(::sendRequest)
            }, 0, 10 * 1000 * 60

    )

}