package men.brakh.bsuirapi.model.bsuirapi

import com.google.gson.annotations.SerializedName
import men.brakh.bsuirapicore.model.data.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Time
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/*  DTO:  */

/**
 * WARNING. There are many crutches in the classes. Thanks to IIS
 */

val logger: Logger = LoggerFactory.getLogger("BsuirApiDto")

private fun String.toDate(): Date? {
    return try {
        val date = SimpleDateFormat("dd.MM.yyyy").parse(this)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.time
    } catch (e: ParseException) {
        null
    }
}


data class AuthorizationDto(val loggedIn: Boolean, val username: String, val fio: String, val message: String)

data class PersonalInformationDto(val name: String, val group: String, val studentRecordBookNumber: String, val photoUrl: String)

data class DiplomaDto(val name: String?, val theme: String?) {
    fun toDiplomaInfoObject() = DiplomaInfo(topic = theme, teacher = name)
}

data class SkillDto(val id: Int, val name: String)

data class ReferenceDto(val id: Int, val name: String, val reference: String)

data class MarkDto(val subject: String,
                   val formOfControl: String?,
                   val hours: String,
                   val mark: String,
                   val date: String,
                   val teacher: String?,
                   val commonMark: Double?,
                   val commonRetakes: Double?,
                   val retakesCount: Int,
                   val idSubject: Int,
                   val idFormOfControl: Int
)

data class MarkPageDto(val averageMark: Double, val marks: List<MarkDto>) {
    fun isEmpty() = marks.isEmpty()

}

data class MarkBookDto(val number: String,
                       val averageMark: Double,
                       val markPages: Map<String, MarkPageDto>) {

    fun toRecordBookObject() = RecordBook(
                number = number,
                averageMark = averageMark,
                semesters = markPages.mapNotNull { (semester, marksPage) ->
                    val marks = marksPage.marks.mapNotNull {
                        if(it.formOfControl != null) {
                            val subjectStatistic = SubjectStatistic(averageMark = it.commonMark, retakeProbability = it.commonRetakes)

                            Mark(subject = it.subject,
                                    date = it.date.toDate(),
                                    formOfControl = it.formOfControl,
                                    hours = it.hours.toDoubleOrNull()?.roundToInt(),
                                    mark = if (it.mark == "") null else it.mark,
                                    retakesCount = it.retakesCount,
                                    teacher = it.teacher,
                                    statistic = if(subjectStatistic.averageMark == null && subjectStatistic.retakeProbability == null)
                                        null
                                    else
                                        subjectStatistic
                            )
                        }
                        else
                            null
                    }
                    if(marksPage.isEmpty())
                        null
                    else
                        Semester(number = semester.toInt(), averageMark = marksPage.averageMark, marks = marks)
                }
        )
    }


data class PersonalCVDto(val id: Int,
                         val firstName: String,
                         val lastName: String,
                         val middleName: String,
                         val birthDate: String,
                         val photoUrl: String?,
                         val summary: String?,
                         val ratting: Int,
                         val faculty: String,
                         val cource: Int,
                         val speciality: String,
                         val studentGroup: String,

                         val showRating: Boolean,
                         val published: Boolean,
                         val searchJob: Boolean,

                         val skills: List<SkillDto>,
                         val references: List<ReferenceDto>
) {
    fun toUserInfoObject() = UserInfo(
            id = id,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            birthDay = birthDate.toDate()!!,
            summary = summary,
            education = StudyInfo(
                    faculty = faculty,
                    course = cource,
                    speciality = speciality,
                    group = studentGroup
            ),
            photo = photoUrl,
            rating = ratting,
            references = references.map { Reference(id = it.id, name = it.name, reference = it.reference) },
            skills = skills.map { Skill(id = it.id, name = it.name)},
            settings = toUserSettings()
    )


    fun toUserSettings() = UserSettings(
            isPublicProfile = published,
            isSearchJob = searchJob,
            isShowRating = showRating
    )
}

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
