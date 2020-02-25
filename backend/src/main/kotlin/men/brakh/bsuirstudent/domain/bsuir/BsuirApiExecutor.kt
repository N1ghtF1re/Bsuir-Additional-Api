package men.brakh.bsuirstudent.domain.bsuir

import com.fasterxml.jackson.databind.ObjectMapper
import men.brakh.bsuirstudent.application.exception.IisException
import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import men.brakh.bsuirstudent.security.authentication.credentials.useBsuirCredentials
import org.apache.http.HttpMessage
import org.apache.http.HttpResponse
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.*
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.HttpClientBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Reader


@Component
class BsuirApiExecutor (
    @Value("\${bsuirapi.host}") val host: String,
    private val objectMapper: ObjectMapper
) {
    private val logger: Logger = LoggerFactory.getLogger(BsuirApiExecutor::class.java)

    // EXTENSION:
    private fun <T : HttpMessage> T.jsonRequest(): T {
        this.setHeader("Accept", "application/json")
        this.setHeader("Content-type", "application/json; charset=UTF-8")

        return this
    }

    private fun <T: Reader> T.readAsString(): String {
        var intValueOfChar: Int
        val targetString = StringBuilder()
        while (this.read().also { intValueOfChar = it } != -1) {
            targetString.append(intValueOfChar.toChar())
        }

        return targetString.toString()
    }

    private fun <T : HttpResponse> T.isSuccessful(): Boolean {
        return this.statusLine.statusCode in 200..299
    }

    fun <T> HttpEntityEnclosingRequestBase.setJsonEntity(obj: T) {
        this.entity = EntityBuilder.create()
            .setContentType(ContentType.APPLICATION_JSON)
            .setContentEncoding("UTF-8")
            .setText( objectMapper.writeValueAsString(obj) )
            .build()
    }

    private fun httpClient() = HttpClientBuilder
        .create()
        .build()

    private fun <T : HttpMessage> T.authorize(username: String, password: String): T{
        val token = tryAuth(username, password) ?: throw UnauthorizedException("Unauthorized")

        this.setHeader("Cookie", "JSESSIONID=$token")
        return this
    }

    private fun throwIfInvalidRequest(resp: CloseableHttpResponse) {
        if (!resp.isSuccessful()) {
            throw IisException(
                resp.entity.content.reader().readAsString()
            )
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
                throwIfInvalidRequest(resp)

                val respReader = resp.entity.content.reader()
                val authDto = objectMapper.readValue<AuthorizationDto>(respReader, AuthorizationDto::class.java)

                return if(authDto.loggedIn) {
                    val header = resp.getHeaders("Set-Cookie").first()
                    getToken(header.value)
                } else {
                    null
                }
            }

        }

    }

    fun <T : Any> makeUnauthorizedRequest(request: HttpRequestBase, resClass: Class<T>): T {
        httpClient().use { client ->
            val req = request
                .jsonRequest()

            client.execute(req).use { resp ->
                throwIfInvalidRequest(resp)

                return objectMapper.readValue(resp.entity.content.reader(), resClass)
            }
        }
    }


    fun <T : Any> makeAuthorizedRequest(request: HttpRequestBase, resClass: Class<T>): T {
        useBsuirCredentials { username, password ->
            httpClient().use { client ->
                val get = request
                    .jsonRequest()
                    .authorize(username, password)

                client.execute(get).use { resp ->

                    throwIfInvalidRequest(resp)

                    val result: T = objectMapper.readValue(resp.entity.content.reader(), resClass)

                    if (result is UsernameAware) {
                        result.username = username
                    }


                    return result
                }
            }
        }

        throw IllegalArgumentException()
    }



    inline fun <reified T : Any> makeAuthorizedGetRequest(url: String): T
        = makeAuthorizedRequest(HttpGet("${host}${url}"),  T::class.java)

}