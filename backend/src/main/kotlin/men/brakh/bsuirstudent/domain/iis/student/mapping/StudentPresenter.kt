package men.brakh.bsuirstudent.domain.iis.student.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.student.*
import men.brakh.bsuirstudent.domain.iis.student.settings.StudentSettingsPresenter
import org.springframework.stereotype.Component

@Component
class StudentPresenter(
    private val studentSettingsPresenter: StudentSettingsPresenter
) : EntityPresenter<Student, StudentDto>{

    override fun mapToDto(entity: Student, dtoClass: Class<out StudentDto>): StudentDto {
        return StudentDto(
            firstName = entity.firstName,
            middleName = entity.middleName,
            lastName = entity.lastName,
            summary = entity.summary,
            rating = entity.rating,
            birthDay = entity.birthDay,
            photo = entity.photo,
            id = entity.id ?: -1,
            educationInfo = EducationInformationDto(
                course = entity.educationInfo!!.course,
                speciality = entity.educationInfo!!.speciality,
                group = entity.educationInfo!!.group,
                faculty = entity.educationInfo!!.faculty
            ),
            settings = studentSettingsPresenter.mapToDto(entity.settings!!, UserSettingsDto::class.java),
            references = entity.references.map {
                StudentReferenceDto(
                    name = it.name,
                    reference = it.reference,
                    id = it.id ?: -1
                )
            },
            skills = entity.skills.map {
                StudentSkillDto(
                    id = it.id ?: -1,
                    name = it.name
                )
            }
        )
    }
}