package men.brakh.bsuirstudent.application.event

import men.brakh.bsuirstudent.domain.BaseEntity

class EntityDeletedEvent(entityId: Any, entityClass: Class<out BaseEntity<*>>) : BaseEntityEvent(entityId, entityClass)