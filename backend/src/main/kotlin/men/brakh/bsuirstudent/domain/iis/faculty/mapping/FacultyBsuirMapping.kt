package men.brakh.bsuirstudent.domain.iis.faculty.mapping

import men.brakh.bsuirstudent.application.bsuir.FacultyBsuirDto
import men.brakh.bsuirstudent.application.bsuir.SpecialityBsuirDto
import men.brakh.bsuirstudent.domain.iis.faculty.EducationForm
import men.brakh.bsuirstudent.domain.iis.faculty.Faculty
import men.brakh.bsuirstudent.domain.iis.faculty.Speciality
import org.springframework.stereotype.Component

@Component
class FacultyBsuirMapping {
    fun mapSpeciality(specialityBsuirDto: SpecialityBsuirDto, faculty: Faculty): Speciality {
        return Speciality(
            id = specialityBsuirDto.id,
            alias = specialityBsuirDto.abbrev,
            name = specialityBsuirDto.name,
            educationForm = when(specialityBsuirDto.educationForm.id) {
                1 -> EducationForm.FULLTIME
                2 -> EducationForm.EXTRAMURAL
                3 -> EducationForm.DISTANCE
                else -> EducationForm.UNKNOWN
            },
            faculty = faculty,
            iisId = specialityBsuirDto.id
        )
    }
    fun mapToFaculties(facultiesBsuirDto: List<FacultyBsuirDto>, specialitiesDto: List<SpecialityBsuirDto>): List<Faculty> {
        val facultiesMap = facultiesBsuirDto.groupBy { it.id }

        return specialitiesDto.groupBy { facultiesMap[it.facultyId]?.first() }
            .mapNotNull { (faculty, specialities) ->
                faculty?.let {
                    val facultyEntity = Faculty(
                        id = faculty.id,
                        alias = faculty.abbrev,
                        name = faculty.name
                    )
                    facultyEntity.specialities = specialities.map { mapSpeciality(it, facultyEntity) }

                    facultyEntity
                }}
    }
}