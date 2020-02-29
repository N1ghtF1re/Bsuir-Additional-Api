package men.brakh.bsuirstudent.domain.iis.auditoriums

import men.brakh.bsuirstudent.domain.Dto


/**
 * ENTITIES:
 */

data class AuditoriumDto(
    val name: String,
    val type: LessonType,
    val floor: Int,
    val building: Int
) : Dto