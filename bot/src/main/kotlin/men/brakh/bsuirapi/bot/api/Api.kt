package men.brakh.bsuirapi.bot.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import men.brakh.bsuirapi.bot.FreeAudsRequest
import men.brakh.bsuirapi.bot.ResourceLoader
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.LoggerFactory
import java.util.*

object Api {
    private val host: String

    private val logger = LoggerFactory.getLogger(Api::class.java)

    private var audsListType = object : TypeToken<List<AuditoriumDto>>() {}.type
    private var buildingsListType = object : TypeToken<List<BuildingDto>>() {}.type


    init {
        val properties = Properties()

        properties.load(ResourceLoader.load("application.properties"))

        host = properties.getProperty("api.host")
    }

    private fun getRequest(url: String): String {
        logger.info("GET request to $url")
        HttpClientBuilder
                .create()
                .build()
                .use { httpClient ->
                    val get = HttpGet(url)

                    httpClient.execute(get).use { resp ->
                        return resp.entity.content.reader().readText()
                    }
                }
    }

    fun getFreeAuds(request: FreeAudsRequest): List<AuditoriumDto> {
        val response = getRequest("$host/auditoriums/free${request.toRequestParams()}")

        return Gson().fromJson(response, audsListType)
    }

    fun getBuildings(): List<BuildingDto> {
        val response = getRequest("$host/buildings")

        return Gson().fromJson(response, buildingsListType)
    }
}