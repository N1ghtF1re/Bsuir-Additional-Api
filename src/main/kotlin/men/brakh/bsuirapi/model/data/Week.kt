package men.brakh.bsuirapi.model.data

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

