package men.brakh.bsuirstudent.notification.sender

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.notification.repository.NotificationTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GlobalNotificationSender(
    notificationSenders: List<NotificationSender>,
    private val notificationTokenRepository: NotificationTokenRepository
) {
    private val logger = LoggerFactory.getLogger(GlobalNotificationSender::class.java)

    private val notificationSendersMap = notificationSenders.mapNotNull { notificationSender ->
        notificationSender.supportedTokenType?.let { it to notificationSender }
    }.toMap()

    fun send(user: Student, title: String, body: String) {
        logger.info("Notification to ${user.id}. Title: $title. Body: $body")
        notificationTokenRepository.findAllByStudent(user)
            .forEach { token ->
                notificationSendersMap[token.type]?.send(token, title, body)
                    ?: throw BadRequestException("Unsupported token type: ${token.type}")
            }

    }
}