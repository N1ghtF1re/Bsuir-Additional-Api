package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.Lesson
import men.brakh.bsuirapi.freeauds.model.Weeks
import java.sql.Time

interface LessonRepository: Repository<Lesson>{
    fun find(
            weeks: Weeks? = null,
            startTime: Time? = null,
            endTime: Time? = null,
            aud: Auditorium? = null,
            day: Int? = null,
            building: Int? = null,
            floor: Int? = null
    ): List<Lesson>

    /*fun findByWeek(weeks: Weeks): List<Lesson>
    fun findBetweenTimes(time1: Time, time2: Time): List<Lesson>
    fun findByAud(aud: Auditorium): List<Lesson>*/
}