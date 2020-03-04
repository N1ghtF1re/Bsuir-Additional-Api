package men.brakh.bsuirstudent.notification

data class NotificationSubscriptionDto (
    val token: String,
    val tokenType: NotificationTokenType
)
data class NotificationUnSubscriptionDto (
    val token: String,
    val tokenType: NotificationTokenType
)