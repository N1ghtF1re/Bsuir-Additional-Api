package men.brakh.bsuirstudent.application.feedback

import men.brakh.bsuirstudent.application.logging.LoggingContext
import org.apache.http.client.utils.URIBuilder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*

@Service
class DeveloperConnectionViaVkService(
    @Value("\${feedback.vk.token}") private val token: String,
    @Value("\${feedback.vk.peer}") private val peer: String,
    private val loggingContext: LoggingContext
) : DeveloperConnectionService {
    private val logger = LoggerFactory.getLogger(DeveloperConnectionService::class.java)
    private val random = Random()

    private fun generateUrl(msg: String): URL {
        return URIBuilder().setScheme("https").setHost("api.vk.com").setPath("/method/messages.send")
                .setParameter("access_token", token)
                .setParameter("v", "5.103")
                .setParameter("peer_id", peer)
                .setParameter("message", msg)
                .setParameter("random_id", random.nextInt().toString())
                .build()
                .toURL()
    }

    override fun write(s: String) {
        val url = generateUrl(s)


        val answer = url.readText()

        logger.info("Feedback was sent to vk: ", answer)
    }

    override fun handleError(ex: Throwable, msg: String?) {
        val text = """
            Incomprehensible error on server.

            Exception: ${ex.javaClass.name}
            Exception Message: ${ex.message}
            Additional Message: $msg
            Session ID: ${loggingContext.sessionId}
            User: ${loggingContext.authenticatedUser}
            URL: ${loggingContext.uri}

            Stacktrace first function: ${ex.stackTrace.firstOrNull()}
        """

        write(text)
    }
}