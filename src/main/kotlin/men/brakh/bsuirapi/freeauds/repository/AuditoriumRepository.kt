package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.data.Auditorium
import men.brakh.bsuirapi.freeauds.model.data.LessonType

interface AuditoriumRepository: Repository<Auditorium> {
    fun find(
            building: Int? = null,
            floor: Int? = null,
            name: String? = null,
            type: LessonType? = null
    ): List<Auditorium>
}