package men.brakh.bsuirstudent.domain.iis.student.service

import men.brakh.bsuirstudent.domain.iis.student.StudentDto

interface StudentService {
    fun getMe() : StudentDto
    fun getStudent(iisId: Int): StudentDto
}