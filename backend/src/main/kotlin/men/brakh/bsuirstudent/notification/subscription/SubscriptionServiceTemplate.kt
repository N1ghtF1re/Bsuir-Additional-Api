package men.brakh.bsuirstudent.notification.subscription

import men.brakh.bsuirstudent.application.event.BaseEntityEvent
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.notification.sender.GlobalNotificationSender
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

abstract class SubscriptionServiceTemplate (
    private val globalNotificationSender: GlobalNotificationSender
){
    abstract val supportedEvents: List<Class<out Any>>
    abstract val supportedEntityClasses: List<Class<out BaseEntity<*>>>
    abstract fun getSubscribers(event: BaseEntityEvent): List<Student>
    abstract fun getNotificationTitle(event: BaseEntityEvent): String
    abstract fun getNotificationBody(event: BaseEntityEvent): String

    @Async
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    open fun handleEvent(event: BaseEntityEvent) {
        if (isSupported(event)) {
            val subscribers = getSubscribers(event)
            val notificationTitle = getNotificationTitle(event)
            val notificationBody = getNotificationBody(event)
            subscribers.forEach { subscriber ->
                globalNotificationSender.send(
                    subscriber,
                    notificationTitle,
                    notificationBody
                )
            }
        }
    }

    private fun isSupported(event: BaseEntityEvent) =
        supportedEvents.any { event::class.java == it } && supportedEntityClasses.any { event.entityClass == it }
}