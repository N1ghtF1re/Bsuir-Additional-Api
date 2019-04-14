package men.brakh.bsuirapi.freeauds.model

/**
 * Значение - битовая маска
 */
enum class WeekNumber(val weekMask: Int) {
    WEEK_ANY(0b1111),
    WEEK_FIRST(0b0001),
    WEEK_SECOND(0b0010),
    WEEK_THIRD(0b0100),
    WEEK_FOURTH(0b1000)
}

class Weeks(val weeks: Array<WeekNumber>) {
    val value: Int
        get() {
            var result = 0
            weeks.forEach {
                result = result or it.weekMask
            }
            return result
        }
}

