package men.brakh.bsuirstudent.application.service;

import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto


interface EntityUpdateService<D : Dto, U : UpdateDto, I> {
    fun update(id: I, updateRequest: U): D
}
