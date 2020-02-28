package men.brakh.bsuirstudent.domain.iis.auditoriums

import men.brakh.bsuirstudent.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


/**
 * Type of lesson
 */
enum class LessonType {
    LESSON_LECTURE,
    LESSON_LAB,
    LESSON_PRACTICE;
}


/**
 * ENTITIES:
 */
@Entity
data class Auditorium(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val name: String,
    val type: LessonType,
    val floor: Int,
    val building: Int

) : BaseEntity<Int> {
    companion object {
        fun isCorrectName(name: String): Boolean {
            val regex = Regex(pattern = "\\d{2,3}([а-г]|-\\d)?$")
            return regex.matches(input = name)
        }
    }
}




