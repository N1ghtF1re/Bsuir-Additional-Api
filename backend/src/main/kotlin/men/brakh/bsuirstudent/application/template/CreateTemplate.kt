package men.brakh.bsuirstudent.application.template

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.mapping.mapper.CreateDtoMapper
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.application.validation.utils.ValidationUtils
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.JpaRepository
import javax.validation.Validator

/**
 * Template for data creation.
 * @param <T> Entity type
 * @param <R> Create Request DTO
 * @param <D> DTO which will be returned after creation
 */
open class CreateTemplate<T : BaseEntity<out Any>, R : CreateDto, D : Dto>(
    private val repository: JpaRepository<T, *>?,
    private val dtoMapper: CreateDtoMapper<R, T>,
    private val entityPresenter: EntityPresenter<T, D>,
    private val validator: Validator? = null
) {

    companion object {
        private val logger = LoggerFactory.getLogger(CreateTemplate::class.java)
    }


    /**
     * Before saving hook.
     * @param entity entity
     * @param request request
     */
    protected fun beforeSaving(entity: T, request: R) {
        ValidationUtils.validateAndThowIfInvalid(validator, entity)
    }

    /**
     * After saving hook.
     * @param entity saved entity.
     */
    protected fun afterSaving(entity: T) {}

    /**
     * Save entity in db and return mepped to dto
     * @param request creation request
     * @param dtoClass class of dto which need to return
     * @return dto
     * @throws BadRequestException if something went wrong.
     */
    fun save(request: R, dtoClass: Class<out D>): D {
        var entity: T = dtoMapper.mapToEntity(request)

        beforeSaving(entity, request)

        entity = try {
            repository!!.save(entity)
        } catch (e: Exception) {
            logger!!.error(
                entity.javaClass.getSimpleName().toString() + " " + entity.toString() + "creation error",
                e
            )
            throw BadRequestException(e)
        }
        afterSaving(entity)
        return entityPresenter.mapToDto(entity, dtoClass)
    }



}