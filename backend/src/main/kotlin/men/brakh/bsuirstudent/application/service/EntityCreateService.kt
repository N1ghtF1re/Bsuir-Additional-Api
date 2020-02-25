package men.brakh.bsuirstudent.application.service;

import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto


interface EntityCreateService<D : Dto, C : CreateDto> {
    fun create(createRequest: C): D
}