package men.brakh.bsuirapicore.extentions

import java.sql.Timestamp
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

fun Date.toTimestamp(): Timestamp {
    return Timestamp(this.time)
}

fun Timestamp.toDate(): Date {
    return Date(this.time)
}