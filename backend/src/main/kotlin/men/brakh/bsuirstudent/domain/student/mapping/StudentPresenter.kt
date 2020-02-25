package men.brakh.bsuirstudent.domain.student.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.student.EducationInformationDto
import men.brakh.bsuirstudent.domain.student.Student
import men.brakh.bsuirstudent.domain.student.StudentDto
import men.brakh.bsuirstudent.domain.student.UserSettingsDto
import org.springframework.stereotype.Component

@Component
class StudentPresenter : EntityPresenter<Student, StudentDto>{

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
            settings = UserSettingsDto(
                isShowRating = entity.settings!!.isShowRating,
                isSearchJob = entity.settings!!.isSearchJob,
                isPublicProfile = entity.settings!!.isPublicProfile
            )
        )
    }
}