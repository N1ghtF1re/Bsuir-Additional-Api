package men.brakh.bsuirstudent.application.service;

import men.brakh.bsuirstudent.application.mapping.mapper.CreateDtoMapper
import men.brakh.bsuirstudent.application.mapping.mapper.DtoMapper
import men.brakh.bsuirstudent.application.mapping.mapper.UpdateDtoMapper
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.application.template.CreateTemplate
import men.brakh.bsuirstudent.application.template.DeleteTemplate
import men.brakh.bsuirstudent.application.template.GetTemplate
import men.brakh.bsuirstudent.application.template.UpdateTemplate
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto
import org.springframework.data.jpa.repository.JpaRepository
import javax.validation.Validator

/**
 * Abstract CRUD Service
 * @param <T> Entity Class
 * @param <D> Presented DTO Class
 * @param <C> Creation Request DTO Class
 * @param <U> Updating Request DTO Class
 * @param <I> Identifier's type
 * */
abstract class AbstractCRUDEntityService<T : BaseEntity<I>, D : Dto, C : CreateDto, U : UpdateDto, I : Any>(
    crudRepository: JpaRepository<T, I>,
    dtoMapper: DtoMapper<D, T>,
    entityPresenter: EntityPresenter<T, D>,
    validator: Validator? = null
) : CRUDEntityService<D, C, U, I> {
    protected val createTemplate: CreateTemplate<T, C, D> = CreateTemplate(
        crudRepository,
        dtoMapper as CreateDtoMapper<C, T>,
        entityPresenter,
        validator
    )
    protected val updateTemplate: UpdateTemplate<T, U, D, I> = UpdateTemplate(
        crudRepository,
        dtoMapper as UpdateDtoMapper<U, T>,
        entityPresenter,
        validator
    )
    protected val getTemplate: GetTemplate<T, D, I> = GetTemplate(entityPresenter, crudRepository)
    protected val deleteTemplate: DeleteTemplate<T, I> = DeleteTemplate(crudRepository)

}
