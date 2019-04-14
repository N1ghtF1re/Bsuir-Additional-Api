package men.brakh.bsuirapi.freeauds.model

import java.util.*

data class Auditorium(var id: Long = -1, val name: String, val type: String, val floor: Int, val building: Int)

data class Lesson(val id: Long = -1, val aud: Auditorium, val weeks: Weeks,
                  val startTime: Date, val endTime: Date, val group: String)

