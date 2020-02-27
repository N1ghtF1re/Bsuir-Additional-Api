package men.brakh.bsuirstudent.domain.iis.student.service

import men.brakh.bsuirstudent.domain.iis.student.StudentDto
import men.brakh.bsuirstudent.domain.iis.student.UpdateUserSettingsRequest
import men.brakh.bsuirstudent.domain.iis.student.UserSettingsDto

interface StudentService {
    fun getMe() : StudentDto
    fun getStudent(iisId: Int): StudentDto

    fun getSettings(): UserSettingsDto
    fun updateSettings(request: UpdateUserSettingsRequest): UserSettingsDto
}