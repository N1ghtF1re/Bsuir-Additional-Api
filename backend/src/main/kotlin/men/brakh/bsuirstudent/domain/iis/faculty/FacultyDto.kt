package men.brakh.bsuirstudent.domain.iis.faculty

import men.brakh.bsuirstudent.domain.Dto

data class FacultyDto(
    val name: String,
    val alias: String,

    val specialities: List<SpecialityDto>
) : Dto

data class SpecialityDto(
    val name: String,

    val alias: String,

    val educationForm: EducationFormDto
) : Dto

enum class EducationFormDto {
    EXTRAMURAL,
    FULLTIME,
    DISTANCE,
    UNKNOWN
}