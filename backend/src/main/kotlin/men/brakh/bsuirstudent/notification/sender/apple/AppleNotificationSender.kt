package men.brakh.bsuirstudent.notification.sender.apple

import com.turo.pushy.apns.ApnsClient
import com.turo.pushy.apns.ApnsClientBuilder
import com.turo.pushy.apns.auth.ApnsSigningKey
import com.turo.pushy.apns.util.ApnsPayloadBuilder
import com.turo.pushy.apns.util.SimpleApnsPushNotification
import com.turo.pushy.apns.util.TokenUtil
import men.brakh.bsuirstudent.notification.NotificationToken
import men.brakh.bsuirstudent.notification.NotificationTokenType
import men.brakh.bsuirstudent.notification.sender.NotificationSender
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.ExecutionException

@Component
class AppleNotificationSender(
    private val config: AppleNotificationConfiguration
) : NotificationSender {
    override val supportedTokenType: NotificationTokenType = NotificationTokenType.APPLE_TOKEN
    private val logger = LoggerFactory.getLogger(AppleNotificationSender::class.java)

    override fun send(token: NotificationToken, title: String, body: String) {
        logger.info("Sent message (APPLE) " + title + ": " + body + " to user with token " + token.token)

        val apnsClient = try {
            getApnsClient()
        } catch (ex: Exception) {
            when(ex) {
                is IOException, is InvalidKeyException, is NoSuchAlgorithmException -> {
                    logger.error("Error with appclient creating", ex)
                    return
                }
                else -> throw ex
            }
        }

        val payloadBuilder = ApnsPayloadBuilder()
        payloadBuilder.setAlertBody(body)

        val payload = payloadBuilder.buildWithDefaultMaximumLength()
        val appleToken = TokenUtil.sanitizeTokenString(token.token)

        val pushNotification = SimpleApnsPushNotification(appleToken, config.applicationName, payload)

        val sendNotificationFuture
                = apnsClient.sendNotification(pushNotification)


        try {
            val pushNotificationResponse = sendNotificationFuture.get()

            if (pushNotificationResponse.isAccepted) {
                logger.info("Push notification accepted by APNs gateway.")
            } else {
                logger.warn("Notification rejected by the APNs gateway: " + pushNotificationResponse.rejectionReason)

                if (pushNotificationResponse.tokenInvalidationTimestamp != null) {
                    logger.warn("\t…and the token is invalid as of " + pushNotificationResponse.tokenInvalidationTimestamp)
                }
            }
        } catch (e: ExecutionException) {
            logger.error("Failed to send push notification.", e)
        } catch (e: InterruptedException) {
            logger.error("Failed to send push notification.", e)
        }
    }


    private fun getResource(path: String): InputStream {
        return AppleNotificationSender::class.java.getResourceAsStream(path)
            ?: throw FileNotFoundException("Resource not found: $path")
    }

    private fun getApnsClient(): ApnsClient {
        return ApnsClientBuilder()
            .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
            .setSigningKey(
                ApnsSigningKey.loadFromInputStream(
                    getResource(config.p8Path), config.teamId, config.keyId)
            )
            .build()
    }

}