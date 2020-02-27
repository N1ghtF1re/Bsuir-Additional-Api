package men.brakh.bsuirstudent.domain.student.service

import men.brakh.bsuirstudent.domain.student.StudentDto

interface StudentService {
    fun getMe() : StudentDto
}