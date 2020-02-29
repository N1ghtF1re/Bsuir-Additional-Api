package men.brakh.bsuirstudent.domain.iis.auditoriums

import men.brakh.bsuirstudent.domain.BaseEntity
import javax.persistence.*


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

    @Enumerated(EnumType.STRING)
    val type: LessonType,
    val floor: Int,
    val building: Int

) : BaseEntity<Int>




