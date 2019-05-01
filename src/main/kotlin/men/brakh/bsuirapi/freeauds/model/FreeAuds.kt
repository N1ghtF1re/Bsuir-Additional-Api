package men.brakh.bsuirapi.freeauds.model

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import java.util.*















object FreeAuds {
    val audRepo: AuditoriumRepository by lazy { Config.auditoriumRepository }
    val lessonsRepo: LessonRepository by lazy { Config.lessonsRepository }


    fun search(dateTime: Date, building: Int, floor: Int? = null) {
        val allAuds: Set<Auditorium> = audRepo.find(
                building = building,
                floor = floor
        ).toSet()

        /*
        val busyAuds = lessonsRepo.find(
                floor = floor,
                building = building,

        ).map { lesson -> lesson.aud }(
        */
    }
}
