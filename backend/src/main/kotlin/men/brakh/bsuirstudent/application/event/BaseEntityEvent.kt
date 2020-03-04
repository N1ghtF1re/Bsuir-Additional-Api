package men.brakh.bsuirstudent.application.event

import men.brakh.bsuirstudent.domain.BaseEntity
import org.springframework.context.ApplicationEvent

abstract class BaseEntityEvent(
    val entityId: Any,
    val entityClass: Class<out BaseEntity<*>>
): ApplicationEvent(entityId)