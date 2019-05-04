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

class Weeks() {
    var weeks: Array<WeekNumber> = arrayOf()

    val value: Int
        get() {
            var result = 0
            weeks.forEach {
                result = result or it.weekMask
            }
            return result
        }

    fun isContains(num: WeekNumber): Boolean = value and num.weekMask == 1


    constructor(weeks: Array<WeekNumber>) : this() {
        this.weeks = weeks
    }

    constructor(value: Int) : this() {
        if(value == WeekNumber.WEEK_ANY.weekMask) {
            this.weeks = arrayOf(WeekNumber.WEEK_ANY)
        } else {
            val weeks = mutableListOf<WeekNumber>()

            WeekNumber.values().forEach {
                if(isContains(it)) {
                    weeks.add(it)
                }
            }
        }
    }
}


fun Weeks.isSingleDay(): Boolean{
    return this.weeks.size == 1 && !this.weeks.contains(WeekNumber.WEEK_ANY)
}
