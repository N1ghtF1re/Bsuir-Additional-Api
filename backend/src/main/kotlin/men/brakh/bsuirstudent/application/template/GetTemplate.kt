package men.brakh.bsuirstudent.application.template;

import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull


/**
 * Get template.
 * @param <T> Entity type
 * @param <D> Dto presenter type
 * @param <I> Identifier
 */
@SuppressWarnings("unchecked")
open class GetTemplate<T : BaseEntity<I>, D : Dto, I : Any>(
    private val presenter: EntityPresenter<T, D>,
    private val repository: JpaRepository<T, I>
) {

    /**
     * Getting entity by id and presenting as dto
     * @param id id
     * @param dtoClass dto class.
     * @return entity mapped to dto
     * @throws BadRequestException if something went wrong
     */
    fun getById(id: I, dtoClass: Class<D>): D {
        val entity = repository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("Entity "
                    + dtoClass.simpleName.replace("Dto", "") + " with id " + id + "isn't found")

        return presenter.mapToDto(entity, dtoClass);
    }

    fun get(func: () -> T?, dtoClass: Class<D>): D {
        val entity = func()
            ?: throw ResourceNotFoundException("Entity is not found");

        return presenter.mapToDto(entity, dtoClass)
    }
}
