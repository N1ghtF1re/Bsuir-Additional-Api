package men.brakh.bsuirapi.freeauds.model

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import java.sql.Time
import java.util.*




object FreeAuds {
    val audRepo: AuditoriumRepository by lazy { Config.auditoriumRepository }
    val lessonsRepo: LessonRepository by lazy { Config.lessonsRepository }
    val bsuirApi by lazy { BsuirApi }


    fun search(dateTime: Date, building: Int, floor: Int? = null): Set<Auditorium> {
        val allAuds: Set<Auditorium> = audRepo.find(
                building = building,
                floor = floor
        ).toSet()

        val currWeek = bsuirApi.getWeekNumber(dateTime)

        val calCurr = Calendar.getInstance()
        calCurr.time = dateTime
        val dayOfWeek = (calCurr.get(Calendar.DAY_OF_WEEK) - 1 + 6) % 7 // Monday - 0 day


        println( Weeks(arrayOf(WeekNumber.values()[currWeek])).weeks.forEach { println(it) })

        val busyAuds: Set<Auditorium> = lessonsRepo.find(
            day = dayOfWeek,
            time = Time(dateTime.time),
            building = building,
            floor = floor,
            weeks = Weeks(arrayOf(WeekNumber.values()[currWeek]))
        ).map { it.aud }.toSet()


        return allAuds - busyAuds
    }
}
