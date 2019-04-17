package men.brakh.bsuirapi.freeauds.model

import java.sql.Time

data class Auditorium(var id: Long = -1, val name: String, val type: LessonType,
                      val floor: Int, val building: Int)

data class Lesson(var id: Long = -1, val aud: Auditorium, val weeks: Weeks,
                  val startTime: Time, val endTime: Time, val group: String)

