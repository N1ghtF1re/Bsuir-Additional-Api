package men.brakh.bsuirapi.extentions

import java.util.*
import java.util.concurrent.TimeUnit

fun Date.daysBetween(date: Date): Int {
    val diffInMillies = Math.abs(date.time - this.time)

    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS).toInt()
}

fun Date.weeksBetween(date: Date): Int {
    val daysBetween = this.daysBetween(date)

    return daysBetween / 7 + if (daysBetween % 7 == 0) 0 else 1
}
