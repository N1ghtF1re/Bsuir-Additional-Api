package men.brakh.bsuirstudent.application.template;

import men.brakh.bsuirstudent.application.event.EntityDeletedEvent
import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.domain.BaseEntity
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

/**
 * Deleting teamplte
 * @param <T> entity type
 * @param <I> identifier's type
</I></T> */
open class DeleteTemplate<T : BaseEntity<*>, I> (
    private val repository: JpaRepository<T, I>,
    private val eventPublisher: ApplicationEventPublisher? = null
) {

    /**
     * Before deleting hook
     * @param entity entity to delete
     */
    protected fun beforeDeleting(entity: T) {}

    /**
     * After deleting hook
     * @param entity removed enityt.
     */
    protected fun afterDeleting(entity: T) {
        eventPublisher?.publishEvent(EntityDeletedEvent(entity.id!!, entity::class.java))
    }

    /**
     * Delete entity from db
     * @param id id
     * @throws BadRequestException if something went wrong.
     */
    fun delete(id: I) {
        val entity = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Entity isn't found")

        beforeDeleting(entity)
        try {
            repository.deleteById(id)
        } catch (e: Exception) {
            logger.error(
                entity.javaClass.simpleName.toString() + " with id " + id + " deleting error",
                e
            )
            throw BadRequestException(e)
        }
        afterDeleting(entity)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DeleteTemplate::class.java)
    }

}
