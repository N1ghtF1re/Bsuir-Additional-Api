package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Lesson
import men.brakh.bsuirapi.freeauds.model.Weeks
import java.sql.Time

interface LessonRepository: Repository<Lesson>{
    fun findByWeek(weeks: Weeks): List<Lesson>
    fun findBetweenTimes(time1: Time, time2: Time): List<Lesson>
}