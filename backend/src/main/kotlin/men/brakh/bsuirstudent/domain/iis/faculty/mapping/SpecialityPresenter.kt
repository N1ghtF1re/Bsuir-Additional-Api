package men.brakh.bsuirstudent.domain.iis.faculty.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.faculty.EducationFormDto
import men.brakh.bsuirstudent.domain.iis.faculty.Speciality
import men.brakh.bsuirstudent.domain.iis.faculty.SpecialityDto
import org.springframework.stereotype.Component

@Component
class SpecialityPresenter : EntityPresenter<Speciality, SpecialityDto> {
    override fun mapToDto(entity: Speciality, dtoClass: Class<out SpecialityDto>): SpecialityDto {
        return SpecialityDto(
            name = entity.name,
            alias = entity.alias,
            educationForm = EducationFormDto.valueOf(entity.educationForm.toString()),
            id = entity.iisId
        )
    }

}