package men.brakh.bsuirstudent.notification.repository

import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.notification.NotificationToken
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationTokenRepository : JpaRepository<NotificationToken, Int> {
    fun deleteAllByStudentIdAndToken(studentId: Int, token: String)
    fun countAllByStudentIdAndToken(studentId: Int, token: String)
    fun findAllByStudent(student: Student): List<NotificationToken>
}