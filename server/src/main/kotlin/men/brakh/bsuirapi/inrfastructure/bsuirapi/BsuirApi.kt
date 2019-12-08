package men.brakh.bsuirapi.inrfastructure.bsuirapi

import com.google.gson.Gson
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.UserNotFoundException
import men.brakh.bsuirapi.inrfastructure.authorization.PasswordEncrypter
import men.brakh.bsuirapicore.extentions.weeksBetween
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.Lesson
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import org.apache.http.HttpMessage
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.util.*

/* API Class: */
class BsuirApi(private val host: String, private val passwordEncrypter: PasswordEncrypter) {
    private val logger: Logger = LoggerFactory.getLogger(BsuirApi::class.java)




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

    private fun <T : HttpMessage> T.authorize(authorizedUserAuthorizationRequest: UserAuthorizationRequest): T{
        val password = passwordEncrypter.decrypt(authorizedUserAuthorizationRequest.password)
        val token = tryAuth(authorizedUserAuthorizationRequest.login, password)
                ?: throw UnauthorizedException()

        this.setHeader("Cookie", "JSESSIONID=$token")
        return this
    }

    private fun httpClient() = HttpClientBuilder
                .create()
                .build()

    fun getDiploma(userAuthorizationRequest: UserAuthorizationRequest): DiplomaDto {
        httpClient().use { client ->
            val get = HttpGet("$host/portal/markbook/diploma")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            client.execute(get).use { resp ->
                return Gson().fromJson(resp.entity.content.reader(), DiplomaDto::class.java)
            }
        }
    }

    fun getMarkBook(userAuthorizationRequest: UserAuthorizationRequest): MarkBookDto {
        httpClient().use { client ->
            val get = HttpGet("$host/portal/markbook")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            client.execute(get).use { resp ->
                return Gson().fromJson(resp.entity.content.reader(), MarkBookDto::class.java)
            }
        }
    }

    fun getGroupInfo(userAuthorizationRequest: UserAuthorizationRequest): GroupInfoDto {
        httpClient().use { client ->
            val get = HttpGet("$host/portal/groupInfo")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            client.execute(get).use { resp ->
                return Gson().fromJson(resp.entity.content.reader(), GroupInfoDto::class.java)
            }
        }
    }

    fun saveShowRating(userAuthorizationRequest: UserAuthorizationRequest, isShow: Boolean) {
        httpClient().use { client ->
            val put = HttpPut("$host/portal/saveShowRating")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            put.setJsonEntity(mapOf("showRating" to isShow))

            client.execute(put)
        }
    }

    fun savePublished(userAuthorizationRequest: UserAuthorizationRequest, isPub: Boolean) {
        httpClient().use { client ->
            val put = HttpPut("$host/portal/savePublished")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            put.setJsonEntity(mapOf("published" to isPub))

            client.execute(put)
        }
    }

    fun saveSearchJob(userAuthorizationRequest: UserAuthorizationRequest, isSearchJob: Boolean) {
        httpClient().use { client ->
            val put = HttpPut("$host/portal/saveSearchJob")
                    .jsonRequest()
                    .authorize(userAuthorizationRequest)

            put.setJsonEntity(mapOf("searchJob" to isSearchJob))

            client.execute(put)
        }
    }

    fun getUserCV(id: Int): PersonalCVDto {
        httpClient().use { client ->
            val builder = URIBuilder("$host/profiles")
            builder.setParameter("id", id.toString())

            val get = HttpGet(builder.build())
                    .jsonRequest()

            client.execute(get).use { resp ->
                if(resp.statusLine.statusCode == 200) {
                    return Gson()
                            .fromJson(resp.entity.content.reader(), PersonalCVDto::class.java)
                } else {
                    throw UserNotFoundException()
                }
            }

        }
    }

    fun getPersonalCV(authorizedUserAuthorizationRequest: UserAuthorizationRequest): PersonalCVDto {
        httpClient().use { client ->
            val get = HttpGet("$host/portal/personalCV")
                    .jsonRequest()
                    .authorize(authorizedUserAuthorizationRequest)

            client.execute(get).use { resp ->
                return Gson()
                        .fromJson(resp.entity.content.reader(), PersonalCVDto::class.java)
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
            val post = HttpPost("$host/auth/login").jsonRequest()

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

    fun getFaculties(): List<FacultyDto> {
        val json: String = URL("$host/faculties").readText()

        return Gson().fromJson(json, Array<FacultyDto>::class.java).toList()
    }

    fun getSpecialities(): List<SpecialityDto> {
        val json: String = URL("$host/specialities").readText()

        return Gson().fromJson(json, Array<SpecialityDto>::class.java).toList()
    }

    fun getRating(year: Int, specialityId: Int): List<RatingDto> {
        val json: String = URL("$host/portal/allRating?year=$year&sdef=$specialityId").readText()

        return Gson().fromJson(json, Array<RatingDto>::class.java).toList()

    }
}
