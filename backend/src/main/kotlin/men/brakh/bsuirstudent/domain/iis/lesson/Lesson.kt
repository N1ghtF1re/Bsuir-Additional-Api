package men.brakh.bsuirstudent.domain.iis.lesson

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import java.sql.Time
import javax.persistence.*


@Entity(name = "lesson")
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "auditorium_id", referencedColumnName = "id")
    val aud: Auditorium,

    @ElementCollection
    @CollectionTable(name = "lesson_weeks", joinColumns = [JoinColumn(name = "lesson_id")])
    @Column(name = "week")
    val weeks: List<Int>,

    val day: Int,

    val startTime: Time,

    val endTime: Time,

    @Column(name = "`group`")
    val group: String
) : BaseEntity<Int>