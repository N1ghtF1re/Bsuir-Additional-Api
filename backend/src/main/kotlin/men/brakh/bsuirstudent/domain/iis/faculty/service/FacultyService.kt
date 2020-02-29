package men.brakh.bsuirstudent.domain.iis.faculty.service

import men.brakh.bsuirstudent.domain.iis.faculty.FacultyDto

interface FacultyService {
    fun getFaculties(): List<FacultyDto>
}