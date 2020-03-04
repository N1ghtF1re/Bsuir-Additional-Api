package men.brakh.bsuirstudent.notification.service

import men.brakh.bsuirstudent.notification.NotificationSubscriptionDto

interface NotificationService {
    fun subscribe(notificationSubscriptionDto: NotificationSubscriptionDto)
    fun usSubscribe(token: String)
    fun test()
}