package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.model.data.Auditorium
import men.brakh.bsuirapi.model.data.Lesson
import men.brakh.bsuirapi.model.data.Weeks
import java.sql.Time

interface LessonRepository: Repository<Lesson> {
    /**
     * @param time - Время лекции (между началом и концом)
     */
    fun find(
            weeks: Weeks? = null,
            time: Time? = null,
            aud: Auditorium? = null,
            day: Int? = null,
            building: Int? = null,
            floor: Int? = null
    ): List<Lesson>

    /*fun findByWeek(weeks: Weeks): List<Lesson>
    fun findBetweenTimes(time1: Time, time2: Time): List<Lesson>
    fun findByAud(aud: Auditorium): List<Lesson>*/
}