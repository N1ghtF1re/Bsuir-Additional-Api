package men.brakh.bsuirstudent.domain.iis.auditoriums.mapping

import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import men.brakh.bsuirstudent.domain.iis.auditoriums.LessonType
import men.brakh.bsuirstudent.application.bsuir.AuditoriumBsuirDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AuditoriumBsuirMapping {
    val logger = LoggerFactory.getLogger(AuditoriumBsuirMapping::class.java)
    companion object {
        fun isCorrectName(name: String): Boolean {
            val regex = Regex(pattern = "\\d{2,3}([а-гa]|-\\d)?$")
            return regex.matches(input = name)
        }
    }

    fun mapName(rawname: String): String {
        val withoutEngA = rawname.replace("a", "а") // Replacing eng a to russian а
        return withoutEngA.substringBeforeLast("-")
    }


    fun mapToAuditorium(bsuirDto: AuditoriumBsuirDto): Auditorium? {
        if(!isCorrectName(bsuirDto.name)) {
            logger.info("Invalid name: ${bsuirDto.name}")
            return null
        }

        val type: LessonType = when(bsuirDto.auditoryType.abbrev) {
            "лк", "ЛК" -> LessonType.LESSON_LECTURE
            "лб", "кк", "ЛР" -> LessonType.LESSON_LAB
            "пз", "ПЗ", "КПР(Р)" -> LessonType.LESSON_PRACTICE
            else -> throw RuntimeException("Auditorium type ${bsuirDto.auditoryType.abbrev} is undefined")
        }


        return Auditorium(
            name = mapName(bsuirDto.name),
            type = type, floor =
            bsuirDto.name[0].toString().toInt(),
            building = bsuirDto.buildingNumber.id
        )
    }
}