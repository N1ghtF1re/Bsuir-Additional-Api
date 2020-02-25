package men.brakh.bsuirstudent.application.service;

import men.brakh.bsuirstudent.domain.Dto

interface EntityReadService<D : Dto, I> {
    fun getById(id: I): D
}
