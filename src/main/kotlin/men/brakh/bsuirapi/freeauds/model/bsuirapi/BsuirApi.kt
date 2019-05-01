package men.brakh.bsuirapi.freeauds.model.bsuirapi

import com.google.gson.Gson
import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.Lesson
import men.brakh.extentions.weeksBetween
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.util.*


/* API Class: */
object BsuirApi {
    private val logger: Logger = LoggerFactory.getLogger(BsuirApi::class.java)
    private val host = Config.bsuirApiHost

    fun getAuditoriums(): List<Auditorium> {
        val json: String = URL("$host/auditory").readText()
        val auds: List<AuditoriumDto> = Gson().fromJson(json, Array<AuditoriumDto>::class.java).toList()

        return auds.mapNotNull { audDto -> audDto.toAud() }
    }

    fun getGroups(): List<GroupDto> {
        val json: String = URL("$host/groups").readText()

        return Gson().fromJson(json, Array<GroupDto>::class.java).toList()
    }

    fun getSchedule(name: String): List<Lesson>  {
        val json: String = URL("$host//studentGroup/schedule?studentGroup=$name").readText()

        if(json.isEmpty()) return listOf()

        return try {
            Gson().fromJson(json, ScheduleResponseDto::class.java).schedules.flatMap { it.toLessons() }
        } catch (e: Exception) {
            logger.error(
                """
                JSON: $json
                GROUP: $name
                Error: ${e.message}
                """.trimIndent(), e
            )

            listOf()
        }
    }

    fun getWeekNumber(date: Date): Int {
        val calCurr = Calendar.getInstance()
        calCurr.time = date
        calCurr.set(Calendar.HOUR, 12)

        val currDate = calCurr.time

        val currYear = calCurr.get(Calendar.YEAR)
        val currMonth = calCurr.get(Calendar.MONTH)

        val firstSeptemberYear = if(currMonth <= 7) currYear - 1 else currYear

        val firstSeptember = Calendar.Builder()
                .set(Calendar.YEAR, firstSeptemberYear)
                .set(Calendar.MONTH, Calendar.SEPTEMBER)
                .set(Calendar.DAY_OF_MONTH, 1)
                .set(Calendar.HOUR, 12)
                .build()
                .time


        return currDate.weeksBetween(firstSeptember) % 4 + 1
    }
}
