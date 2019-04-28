package men.brakh.bsuirapi.freeauds.model

import java.sql.Time

data class Auditorium(var id: Long = -1, val name: String, val type: LessonType,
                      val floor: Int, val building: Int) {
    companion object {
        fun isCorrectName(name: String): Boolean {
            val regex = Regex(pattern = "\\d{2,3}([а-г]|-\\d)?$")
            return regex.matches(input = name)
        }
    }
}

data class Lesson(var id: Long = -1, val aud: Auditorium, val weeks: Weeks, val day: Int,
                  val startTime: Time, val endTime: Time, val group: String)

