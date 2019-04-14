package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Lesson
import men.brakh.bsuirapi.freeauds.model.Weeks
import java.util.*

interface LessonRepository: Repository<Lesson>{
    fun findByWeek(weeks: Weeks): List<Lesson>
    fun findBetweenTimes(time1: Date, time2: Date): List<Lesson>
    fun findByWeekBetweenTimes(weeks: Weeks, time1: Date, time2: Date): List<Lesson>
}