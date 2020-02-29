package men.brakh.bsuirstudent.domain.iis.faculty.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.faculty.Faculty
import men.brakh.bsuirstudent.domain.iis.faculty.FacultyDto
import men.brakh.bsuirstudent.domain.iis.faculty.SpecialityDto
import org.springframework.stereotype.Component


@Component
class FacultyPresenter(
    private val specialityPresenter: SpecialityPresenter
) : EntityPresenter<Faculty, FacultyDto> {
    override fun mapToDto(entity: Faculty, dtoClass: Class<out FacultyDto>): FacultyDto {
        return FacultyDto(
            name = entity.name,
            alias = entity.alias,
            specialities = specialityPresenter.mapListToDto(entity.specialities, SpecialityDto::class.java)
        )
    }

}