package men.brakh.bsuirstudent.application.bsuir

import org.springframework.stereotype.Component

@Component
class BsuirFacultyService(
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    fun getFaculties(): List<FacultyBsuirDto>
        = bsuirApiExecutor.makeUnauthorizedGetRequest<Array<FacultyBsuirDto>>("/faculties")!!.toList()

    fun getSpecialities(): List<SpecialityBsuirDto>
        = bsuirApiExecutor.makeUnauthorizedGetRequest<Array<SpecialityBsuirDto>>("/specialities")!!.toList()
}