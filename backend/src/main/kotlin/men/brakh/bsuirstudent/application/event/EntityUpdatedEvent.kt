package men.brakh.bsuirstudent.application.event

import men.brakh.bsuirstudent.domain.BaseEntity

class EntityUpdatedEvent(entityId: Any, entityClass: Class<out BaseEntity<*>>) :
    BaseEntityEvent(entityId, entityClass)