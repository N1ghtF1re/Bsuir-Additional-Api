package men.brakh.bsuirstudent.application.mapping.mapper;

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto


interface DtoMapper<D, T> where D : Dto, T : BaseEntity<out Any>{
    fun mapToEntity(dto: D): T
}
