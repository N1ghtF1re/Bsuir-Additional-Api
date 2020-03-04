package men.brakh.bsuirstudent.application.bsuir

import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.extensions.weeksBetween
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
open class BsuirScheduleService (
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    private val logger = LoggerFactory.getLogger(BsuirScheduleService::class.java)


    fun getAuditoriums(): List<AuditoriumBsuirDto>
        = bsuirApiExecutor.makeUnauthorizedGetRequest<Array<AuditoriumBsuirDto>> ("/auditory")!!.toList()

    fun getSchedule(groupName: String): List<DayScheduleBsuirDto> {
        return try {
            (bsuirApiExecutor
                .makeUnauthorizedGetRequest<ScheduleResponseBsuirDto>(
                    "/studentGroup/schedule?studentGroup=$groupName"
                ) ?: throw ResourceNotFoundException("Schedule for group $groupName is not found"))
                .schedules
        } catch (e: Exception) {
            logger.error(
                """
                GROUP: $groupName
                Error: ${e.message}
                """.trimIndent(), e
            )

            listOf()
        }

    }

    fun getGroups(): List<ScheduleBsuirGroupDto>
        = bsuirApiExecutor.makeUnauthorizedGetRequest<Array<ScheduleBsuirGroupDto>>("/groups")!!.toList()

    fun getWeekNumber(date: Date): Int {

        val calCurr = Calendar.getInstance()
        calCurr.time = date
        calCurr.set(Calendar.HOUR, 12)

        val currDate = calCurr.time

        val currYear = calCurr.get(Calendar.YEAR)
        val currMonth = calCurr.get(Calendar.MONTH)

        val firstSeptemberYear = if(currMonth <= MONTH_AUGUST) currYear - 1 else currYear

        val firstSeptember = Calendar.Builder()
            .set(Calendar.YEAR, firstSeptemberYear)
            .set(Calendar.MONTH, Calendar.SEPTEMBER)
            .set(Calendar.DAY_OF_MONTH, 1)
            .set(Calendar.HOUR, 12)
            .build()
            .time


        return currDate.weeksBetween(firstSeptember) % 4 + 1
    }

    companion object {
        const val MONTH_AUGUST = 7;
    }

}
