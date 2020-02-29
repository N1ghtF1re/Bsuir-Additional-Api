package men.brakh.bsuirstudent.domain.iis.auditoriums.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import men.brakh.bsuirstudent.domain.iis.auditoriums.AuditoriumDto
import org.springframework.stereotype.Component

@Component
class AuditoriumPresenter : EntityPresenter<Auditorium, AuditoriumDto> {
    override fun mapToDto(entity: Auditorium, dtoClass: Class<out AuditoriumDto>): AuditoriumDto {
        return AuditoriumDto(
            name = entity.name,
            floor = entity.floor,
            building = entity.building,
            type = entity.type
        )
    }
}