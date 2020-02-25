package men.brakh.bsuirstudent.domain.student

import com.fasterxml.jackson.annotation.JsonFormat
import men.brakh.bsuirstudent.domain.Dto
import java.util.*

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

    val educationInfo: EducationInformationDto,

    val settings: UserSettingsDto
) : Dto