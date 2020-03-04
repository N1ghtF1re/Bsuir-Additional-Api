package men.brakh.bsuirstudent.notification.sender

import men.brakh.bsuirstudent.notification.NotificationToken
import men.brakh.bsuirstudent.notification.NotificationTokenType

interface NotificationSender {
    val supportedTokenType: NotificationTokenType
    fun send(token: NotificationToken, title: String, body: String)
}