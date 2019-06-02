package men.brakh.bsuirapi.model.bsuirapi

import com.google.gson.annotations.SerializedName
import men.brakh.bsuirapicore.model.data.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Time
import java.text.SimpleDateFormat


/*  DTO:  */

/**
 * WARNING. There are many crutches in the classes. Thanks to IIS
 */

val logger: Logger = LoggerFactory.getLogger("BsuirApiDto")

data class BuildingDto(val id: Int, val name: String)

data class AuditoriumTypeDto(val name: String, val abbrev: String)

data class AuditoriumDto(@SerializedName("name") var rowname: String, val auditoryType: AuditoriumTypeDto, val buildingNumber: BuildingDto) {
    val name: String
        get() {
            val withoutEngA = rowname.replace("a", "а") // Replacing eng a to russian а
            return withoutEngA.substringBeforeLast("-")
        }

    val type: LessonType
        get() = when(auditoryType.abbrev) {
            "лк", "ЛК" -> LessonType.LESSON_LECTURE
            "лб", "кк", "ЛР" -> LessonType.LESSON_LAB
            "пз", "ПЗ", "КПР(Р)" -> LessonType.LESSON_PRACTICE
            else -> throw RuntimeException("Auditorium type ${auditoryType.abbrev} is undefined")
        }

    /**
     * Transforming DTO to usual object
     */
    fun toAud(): Auditorium? {
        if(!Auditorium.isCorrectName(name)) return null

        return Auditorium(name = name, type = type, floor = name[0].toString().toInt(), building = buildingNumber.id)
    }
}

data class GroupDto(val id: Int, val name: String, val course: Int?)


data class ScheduleDto(val weekNumber: List<Int>, val studentGroup: List<String>, val numSubgroup: Int,
                       val auditory: List<String>, val startLessonTime: String, val endLessonTime: String,
                       val subject: String, val note: String?, val lessonType: String)

data class DayScheduleDto(val weekDay: String, val schedule: List<ScheduleDto>) {
    private val day: Int
        get() = arrayOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота").indexOf(weekDay)

    fun toLessons(): List<Lesson> {
        return schedule.flatMap { schedule ->
            schedule.auditory.mapNotNull SkipLesson@{ audRawName ->
                val audCols = audRawName.split("-")

                if(audCols.count() != 2) return@SkipLesson null

                val audName = audCols[0]
                val building = audCols[1].toIntOrNull()

                if(building == null) {
                    logger.warn("Invalid auditorium $audRawName. Auditorium skipped")
                    return@SkipLesson null
                }

                val aud: Auditorium? = AuditoriumDto(
                        rowname = audName,
                        auditoryType = AuditoriumTypeDto(name = schedule.lessonType, abbrev = schedule.lessonType),
                        buildingNumber = BuildingDto(building, building.toString())
                ).toAud()

                if(aud == null){
                    logger.warn("Invalid auditorium $audRawName. Auditorium skipped")
                    return@SkipLesson null
                }

                val weeks = Weeks(schedule.weekNumber.map { WeekNumber.values()[it] })

                val sdf = SimpleDateFormat("HH:mm")

                Lesson(
                        aud = aud,
                        weeks = weeks,
                        day = day,
                        startTime = Time(sdf.parse(schedule.startLessonTime).time),
                        endTime = Time(sdf.parse(schedule.endLessonTime).time),
                        group = schedule.studentGroup.first()
                )
            }
        }
    }
}


data class ScheduleResponseDto(val schedules: List<DayScheduleDto>)
