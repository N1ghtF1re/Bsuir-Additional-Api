package men.brakh.bsuirstudent.application.template

import men.brakh.bsuirstudent.application.event.EntityUpdatedEvent
import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.application.mapping.mapper.UpdateDtoMapper
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.application.validation.utils.ValidationUtils
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import javax.validation.Validator

/**
 * Template for data updating.
 * @param <T> Entity type
 * @param <R> Create Request DTO
 * @param <D> DTO which will be returned after creation
 * @param <I> Entity's identifier.
 * */
open class UpdateTemplate<T : BaseEntity<out Any>, R : UpdateDto, D : Dto, I>(
    private val repository: JpaRepository<T, I>,
    private val dtoMapper: UpdateDtoMapper<R, T>,
    private val entityPresenter: EntityPresenter<T, D>,
    private val validator: Validator? = null,
    private val eventPublisher: ApplicationEventPublisher? = null
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UpdateTemplate::class.java)
    }

    /**
     * Before saving hook
     * @param entity entity
     * @param request request
     * @throws BadRequestException if something went wrong
     */
    protected fun beforeSaving(entity: T, request: R) {
        ValidationUtils.validateAndThowIfInvalid(validator, entity)
    }

    /**
     * After saving hook
     * @param entity entity
     */
    protected fun afterSaving(entity: T) {
        eventPublisher?.publishEvent(EntityUpdatedEvent(entity.id!!, entity::class.java))
    }

    /**
     * Update entity by id and return updated entity mapped to dto
     * @param id id
     * @param request updating request
     * @param dtoClass dto class
     * @return updated entity mapped to dto
     * @throws BadRequestException if something went wrong
     */
    fun update(id: I, request: R, dtoClass: Class<out D>): D {
        val entityFromRepo = repository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Entity isn't found")
        val updatedEntity = dtoMapper.mapToEntity(entityFromRepo, request)

        beforeSaving(updatedEntity, request)

        val savedEntity = try {
            repository.saveAndFlush(updatedEntity)
        } catch (e: Exception) {
            logger!!.error(
                updatedEntity.javaClass.simpleName.toString() + " " + javaClass.toString() + " updating error",
                e
            )
            throw BadRequestException(e)
        }
        afterSaving(savedEntity)
        return entityPresenter.mapToDto(savedEntity, dtoClass)
    }

}