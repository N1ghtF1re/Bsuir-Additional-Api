package men.brakh.bsuirstudent.notification

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.student.Student
import javax.persistence.*

enum class NotificationTokenType {
    APPLE_TOKEN
}

@Entity
@Table(name = "notification_token")
data class NotificationToken(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    @Enumerated(EnumType.STRING)
    val type: NotificationTokenType,

    val token: String
)