package men.brakh.bsuirstudent.domain.iis.lesson.mapping

import men.brakh.bsuirstudent.application.bsuir.*
import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import men.brakh.bsuirstudent.domain.iis.auditoriums.mapping.AuditoriumBsuirMapping
import men.brakh.bsuirstudent.domain.iis.auditoriums.repository.AuditoriumRepository
import men.brakh.bsuirstudent.domain.iis.lesson.Lesson
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.Time
import java.text.SimpleDateFormat

@Component
class LessonBsuirMapping(
    private val auditoriumBsuirMapping: AuditoriumBsuirMapping,
    private val auditoriumRepository: AuditoriumRepository
) {
    private fun getDayOfWeek(str: String): Int {
        return arrayOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота").indexOf(str)
    }
    private val logger = LoggerFactory.getLogger(LessonBsuirMapping::class.java)
    private val sdf = SimpleDateFormat("HH:mm")

    fun getAud(audName: String, building: Int, schedule: ScheduleBsuirDto): Auditorium? {

        val bsuirAud = AuditoriumBsuirDto(
            name = audName,
            auditoryType = AuditoriumTypeBsuirDto(name = schedule.lessonType, abbrev = schedule.lessonType),
            buildingNumber = BuildingBsuirDto(building, building.toString())
        )

        val aud = auditoriumBsuirMapping.mapToAuditorium(bsuirAud) ?: return null

        return auditoriumRepository.findOneByBuildingAndFloorAndName(
            aud.building, aud.floor, aud.name
        ) ?: auditoriumRepository.save(aud)
    }

    fun mapToLessons(schedules: DayScheduleBsuirDto): List<Lesson> {
        return schedules.schedule.flatMap { schedule ->
            schedule.auditory.mapNotNull SkipLesson@{ audRawName ->
                val audCols = audRawName.split("-")

                if(audCols.count() != 2) return@SkipLesson null

                val audName = audCols[0]
                val building: Int? = audCols[1].toIntOrNull()

                if(building == null) {
                    logger.warn("Invalid auditorium $audRawName. Don't have a building Auditorium skipped")
                    return@SkipLesson null
                }


                val aud: Auditorium? =  synchronized(auditoriumRepository) { getAud(audName, building, schedule) }

                if(aud == null){
                    logger.warn("Invalid auditorium $audRawName. Auditorium skipped")
                    return@SkipLesson null
                }

                val weeks = if (schedule.weekNumber.contains(0))
                    listOf(0,1,2,3)
                else
                    schedule.weekNumber.map { it - 1 }


                Lesson(
                    aud = aud,
                    weeks = weeks,
                    day = getDayOfWeek(schedules.weekDay),
                    startTime = Time(sdf.parse(schedule.startLessonTime).time),
                    endTime = Time(sdf.parse(schedule.endLessonTime).time),
                    group = schedule.studentGroup.first()
                )
            }
        }
    }
}