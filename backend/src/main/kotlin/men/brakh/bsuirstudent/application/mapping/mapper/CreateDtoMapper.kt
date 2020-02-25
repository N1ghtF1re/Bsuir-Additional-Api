package men.brakh.bsuirstudent.application.mapping.mapper;

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.CreateDto


interface CreateDtoMapper<D, T> where D:CreateDto, T: BaseEntity<*> {
    fun mapToEntity(createRequest: D): T
}
