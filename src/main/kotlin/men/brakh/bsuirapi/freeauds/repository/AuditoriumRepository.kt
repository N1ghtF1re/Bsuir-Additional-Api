package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.LessonType

interface AuditoriumRepository: Repository<Auditorium> {
    fun find(
            building: Int? = null,
            floor: Int? = null,
            type: LessonType? = null
    ): List<Auditorium>
}