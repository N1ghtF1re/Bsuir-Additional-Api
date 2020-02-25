package men.brakh.bsuirstudent.application.mapping.mapper;

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.UpdateDto


interface UpdateDtoMapper<D, T> where D : UpdateDto, T : BaseEntity<out Any> {
    fun mapToEntity(entity: T, updateRequest: D): T;
}