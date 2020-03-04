package men.brakh.bsuirstudent.notification.subscription;

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.student.Student

interface Subscription<T, I> : BaseEntity<I> {
    val user: Student
    val entity: T
}
