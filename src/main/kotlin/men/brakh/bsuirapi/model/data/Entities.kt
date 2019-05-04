package men.brakh.bsuirapi.model.data

import java.sql.Time

data class Auditorium(@Transient var id: Long = -1, val name: String, val type: LessonType,
                      val floor: Int, val building: Int) {
    companion object {
        fun isCorrectName(name: String): Boolean {
            val regex = Regex(pattern = "\\d{2,3}([а-г]|-\\d)?$")
            return regex.matches(input = name)
        }
    }
}

data class Lesson(@Transient var id: Long = -1, val aud: Auditorium, val weeks: Weeks, val day: Int,
                  val startTime: Time, val endTime: Time, val group: String)


data class Building(val name: Int, val floors: List<Int>)