package men.brakh.bsuirstudent.application.mapping.presenter;

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto
interface EntityPresenter<T, D> where T: BaseEntity<out Any>, D : Dto{
    fun mapToDto(entity: T, dtoClass: Class<out D>): D
    
    fun mapListToDto(
        entities: List<T>,
        dtoClass: Class<out D>
    ): List<D> {
        return entities.map { mapToDto(it, dtoClass) }
    }
}
