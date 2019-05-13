package men.brakh.bsuirapi.model.data

import java.sql.Time

/**
 * Type of lesson
 */
enum class LessonType {
    LESSON_LECTURE,
    LESSON_LAB,
    LESSON_PRACTICE;
}

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

    fun isSingleDay(): Boolean{
        return this.weeks.size == 1 && !this.weeks.contains(WeekNumber.WEEK_ANY)
    }

}

/**
 * ENTITIES:
 */

data class Auditorium(@Transient override val id: Long = -1, val name: String, val type: LessonType,
                      val floor: Int, val building: Int) : Identifiable {
    companion object {
        fun isCorrectName(name: String): Boolean {
            val regex = Regex(pattern = "\\d{2,3}([а-г]|-\\d)?$")
            return regex.matches(input = name)
        }
    }
}

data class Lesson(@Transient override val id: Long = -1, val aud: Auditorium, val weeks: Weeks, val day: Int,
                  val startTime: Time, val endTime: Time, val group: String) : Identifiable


data class Building(val name: Int, val floors: List<Int>)