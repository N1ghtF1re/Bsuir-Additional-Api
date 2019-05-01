package men.brakh.bsuirapi.freeauds.model

import kotlinx.serialization.internal.ArrayListSerializer
import kotlinx.serialization.json.Json
import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.model.bsuirapi.AuditoriumDto
import men.brakh.bsuirapi.freeauds.model.bsuirapi.GroupDto
import men.brakh.bsuirapi.freeauds.model.bsuirapi.ScheduleResponseDto
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
        val auds: List<AuditoriumDto> = Json.nonstrict.parse(ArrayListSerializer(AuditoriumDto.serializer()), json)

        return auds.mapNotNull { audDto -> audDto.toAud() }
    }

    fun getGroups(): List<GroupDto> {
        val json: String = URL("$host/groups").readText()

        return Json.nonstrict.parse(ArrayListSerializer(GroupDto.serializer()), json)
    }

    fun getSchedule(name: String): List<Lesson>  {
        val json: String = URL("$host//studentGroup/schedule?studentGroup=$name").readText()

        if(json.isEmpty()) return listOf()

        return try {
            Json.nonstrict.parse(ScheduleResponseDto.serializer(), json).schedules.flatMap { it.toLessons() }
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
