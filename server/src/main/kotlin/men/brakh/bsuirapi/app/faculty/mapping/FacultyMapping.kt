package men.brakh.bsuirapi.app.faculty.mapping

import men.brakh.bsuirapi.inrfastructure.bsuirapi.FacultyDto
import men.brakh.bsuirapi.inrfastructure.bsuirapi.SpecialityDto
import men.brakh.bsuirapicore.model.data.EducationForm
import men.brakh.bsuirapicore.model.data.Faculty
import men.brakh.bsuirapicore.model.data.Speciality

fun SpecialityDto.toEntity(): Speciality {
    return Speciality(
            id = this.id,
            alias = this.abbrev,
            name = this.name,
            educationForm = when(this.educationForm.id) {
                1 -> EducationForm.FULLTIME
                2 -> EducationForm.EXTRAMURAL
                3 -> EducationForm.DISTANCE
                else -> EducationForm.UNKNOWN
            }
    )
}

fun FacultyDto.toEntity(allSpecialities: List<SpecialityDto>): Faculty {
    val facultySpecialities = allSpecialities
            .filter { speciality -> speciality.facultyId == this.id }
            .map { it.toEntity() }

    return Faculty(
            id = this.id,
            alias = this.abbrev,
            name = this.name,
            specialities = facultySpecialities
    )
}

fun List<FacultyDto>.toEntitiesList(allSpecialities: List<SpecialityDto>): List<Faculty> {
    val facultiesMap = this.groupBy { it.id }

    return allSpecialities.groupBy { facultiesMap[it.facultyId]?.first() }
            .mapNotNull { (faculty, specialities) ->
                faculty?.let {
                    Faculty(
                            id = faculty.id,
                            alias = faculty.abbrev,
                            name = faculty.name,
                            specialities = specialities.map { it.toEntity() }
                    )
                }}

}