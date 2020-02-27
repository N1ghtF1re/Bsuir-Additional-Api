package men.brakh.bsuirstudent.domain.iis.student

import com.fasterxml.jackson.annotation.JsonFormat
import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto
import java.util.*

data class StudentSkillDto(
    val id: Int,
    val name: String
)

data class StudentReferenceDto(
    val id: Int,
    val name: String,
    val reference: String
)


data class EducationInformationDto  (
    val faculty: String,
    val course: Int,
    val speciality: String,
    val group: String
) : Dto


data class UserSettingsDto(
    val isShowRating: Boolean,
    val isPublicProfile: Boolean,
    val isSearchJob: Boolean
) : Dto

data class UpdateUserSettingsRequest (
    val isShowRating: Boolean,
    val isPublicProfile: Boolean,
    val isSearchJob: Boolean
) : Dto, UpdateDto


data class StudentDto (
    val id: Int,

    val firstName: String,
    val lastName: String,
    val middleName: String?,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val birthDay: Date,

    val photo: String?,
    val summary: String?,
    val rating: Int,

    val skills: List<StudentSkillDto>,
    val references: List<StudentReferenceDto>,

    val educationInfo: EducationInformationDto,

    val settings: UserSettingsDto
) : Dto