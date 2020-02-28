package men.brakh.bsuirstudent.domain.iis.lesson

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import java.sql.Time
import javax.persistence.*

/**
 * Value - bit mask
 */
enum class WeekNumber(val weekMask: Int) {
    WEEK_ANY(0b1111),
    WEEK_FIRST(0b0001),
    WEEK_SECOND(0b0010),
    WEEK_THIRD(0b0100),
    WEEK_FOURTH(0b1000)
}


/**
 * Weeks container
 */
data class Weeks(val mask: Int) {
    val weeks: List<WeekNumber>
        get() = WeekNumber.values().drop(1).filter { mask and it.weekMask != 0 }

    constructor(weeks: List<WeekNumber>) : this(
        weeks.map { it.weekMask }.fold(0) { result, element -> result or element }
    )

    fun toInt(): Int {
        return mask
    }

    fun isSingleDay(): Boolean{
        return this.weeks.size == 1 && !this.weeks.contains(WeekNumber.WEEK_ANY)
    }

}


@Entity(name = "lesson")
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "auditorium_id", referencedColumnName = "id")
    val aud: Auditorium,

    val weeksMask: Int,

    val day: Int,

    val startTime: Time,

    val endTime: Time,

    val group: String
) : BaseEntity<Int>