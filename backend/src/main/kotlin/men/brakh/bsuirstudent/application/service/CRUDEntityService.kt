package men.brakh.bsuirstudent.application.service;

import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto

interface CRUDEntityService<D : Dto, C : CreateDto, U : UpdateDto, I> : EntityCreateService<D, C>,
    EntityUpdateService<D, U, I>, EntityDeleteService<I>, EntityReadService<D, I>
