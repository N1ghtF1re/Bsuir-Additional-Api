package men.brakh.bsuirapi.model.bsuirapi

import com.google.gson.Gson
import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.UserNotFoundException
import men.brakh.bsuirapicore.extentions.weeksBetween
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.Lesson
import men.brakh.bsuirapicore.model.data.User
import men.brakh.bsuirapicore.model.data.UserInfo
import org.apache.http.HttpMessage
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.util.*






/* API Class: */
object BsuirApi {
    private val logger: Logger = LoggerFactory.getLogger(BsuirApi::class.java)
    private val host = Config.bsuirApiHost
    private val passwordEncrypter = Config.passwordEncrypter


    // EXTENSION:
    private fun <T : HttpMessage> T.jsonRequest(): T {
        this.setHeader("Accept", "application/json")
        this.setHeader("Content-type", "application/json; charset=UTF-8")
        return this
    }

    private fun <T> HttpEntityEnclosingRequestBase.setJsonEntity(obj: T) {
        this.entity = EntityBuilder.create()
                .setContentType(ContentType.APPLICATION_JSON)
                .setContentEncoding("UTF-8")
                .setText( Gson().toJson(obj) )
                .build()
    }

    private fun <T : HttpMessage> T.authorize(authorizedUser: User): T{
        val password = passwordEncrypter.decrypt(authorizedUser.password)
        val token = tryAuth(authorizedUser.login, password) ?: throw UnauthorizedException()

        this.setHeader("Cookie", "JSESSIONID=$token")
        return this
    }

    private fun httpClient() = HttpClientBuilder
                .create()
                .build()

    fun getUserInfo(id: Int): UserInfo {
        httpClient().use { client ->
            val builder = URIBuilder("$host/profiles")
            builder.setParameter("id", id.toString())

            val get = HttpGet(builder.build())
                    .jsonRequest()

            client.execute(get).use { resp ->
                if(resp.statusLine.statusCode == 200) {
                    return Gson()
                            .fromJson(resp.entity.content.reader(), PersonalCVDto::class.java)
                            .toUserInfoObject()
                } else {
                    throw UserNotFoundException()
                }
            }

        }
    }

    fun getUserInfo(authorizedUser: User): UserInfo {
        httpClient().use { client ->
            val get = HttpGet("$host/portal/personalCV")
                    .jsonRequest()
                    .authorize(authorizedUser)

            client.execute(get).use { resp ->
                return Gson()
                        .fromJson(resp.entity.content.reader(), PersonalCVDto::class.java)
                        .toUserInfoObject()
            }
        }
    }

    fun tryAuth(login: String, password: String): String? {
        fun getToken(cookie: String): String {
            return cookie.split(";").first().substring("JSESSIONID=".length)
        }

        val client = httpClient()

        val userData = mapOf(
                "password" to password,
                "username" to login
        )

        client.use { httpClient ->
            val post = HttpPost("${host}/auth/login").jsonRequest()

            post.setJsonEntity(userData)

            httpClient.execute(post).use { resp ->
                val respReader = resp.entity.content.reader()
                val authDto = Gson().fromJson(respReader, AuthorizationDto::class.java)

                return if(authDto.loggedIn) {
                    val header = resp.getHeaders("Set-Cookie").first()
                    getToken(header.value)
                } else {
                    null
                }
            }

        }

    }

    fun getAuditoriums(): List<Auditorium> {
        val json: String = URL("$host/auditory").readText()
        val auds: List<AuditoriumDto> = Gson().fromJson(json, Array<AuditoriumDto>::class.java).toList()

        return auds.mapNotNull { audDto -> audDto.toAud() }
    }

    fun getGroups(): List<GroupDto> {
        val json: String = URL("$host/groups").readText()

        return Gson().fromJson(json, Array<GroupDto>::class.java).toList()
    }

    fun getSchedule(name: String): List<Lesson>  {
        val json: String = URL("$host/studentGroup/schedule?studentGroup=$name").readText()

        if(json.isEmpty()) return listOf()

        return try {
            Gson().fromJson(json, ScheduleResponseDto::class.java).schedules.flatMap { it.toLessons() }
        } catch (e: Exception) {
            logger.error(
                """
                JSON: $json
                GROUP: $name
                Error: ${e.message}
                """.trimIndent(), e
            )

            listOf()
        }
    }

    fun getWeekNumber(date: Date): Int {
        val calCurr = Calendar.getInstance()
        calCurr.time = date
        calCurr.set(Calendar.HOUR, 12)

        val currDate = calCurr.time

        val currYear = calCurr.get(Calendar.YEAR)
        val currMonth = calCurr.get(Calendar.MONTH)

        val firstSeptemberYear = if(currMonth <= 7) currYear - 1 else currYear

        val firstSeptember = Calendar.Builder()
                .set(Calendar.YEAR, firstSeptemberYear)
                .set(Calendar.MONTH, Calendar.SEPTEMBER)
                .set(Calendar.DAY_OF_MONTH, 1)
                .set(Calendar.HOUR, 12)
                .build()
                .time


        return currDate.weeksBetween(firstSeptember) % 4 + 1
    }
}

fun main() {
    val user = Config.userRepository.findById(1)!!
    println(BsuirApi.getUserInfo(user))
}