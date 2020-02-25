package men.brakh.bsuirstudent.application.template

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto
import org.springframework.data.jpa.repository.JpaRepository

class CachedGetTemplate<T : BaseEntity<I>, D : Dto, I : Any>(
    private val repository: JpaRepository<T, I>,
    private val presenter: EntityPresenter<T, D>
) {
    private val getTemplate: GetTemplate<T, D, I> = GetTemplate(presenter, repository)

    fun getById(
        getDataFunc: (id: I) -> T,
        id: I,
        dtoClass: Class<D>
    ): D {
        return if (repository.existsById(id)) {
            getTemplate.getById(id, dtoClass);
        } else {
            val entity = getDataFunc(id)
            val savedEntity = repository.save(entity)
            presenter.mapToDto(savedEntity, dtoClass)
        }
    }

    fun get(
        getDataFunc: () -> T,
        findFunc: () -> T?,
        existsFunc: () -> Boolean,
        dtoClass: Class<D>
    ): D {
        return if (existsFunc()) {
            getTemplate.get(findFunc, dtoClass);
        } else {
            val entity = getDataFunc()
            val savedEntity = repository.save(entity)
            presenter.mapToDto(savedEntity, dtoClass)
        }
    }

}