package men.brakh.bsuirapi.repository

import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.LessonType

interface AuditoriumRepository: Repository<Auditorium> {
    fun find(
            building: Int? = null,
            floor: Int? = null,
            name: String? = null,
            type: LessonType? = null
    ): List<Auditorium>
}