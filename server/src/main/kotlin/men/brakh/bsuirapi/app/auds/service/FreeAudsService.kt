package men.brakh.bsuirapi.app.auds.service

import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapi.repository.LessonRepository
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.WeekNumber
import men.brakh.bsuirapicore.model.data.Weeks
import java.sql.Time
import java.util.*


class FreeAudsService(
        private val audRepo: AuditoriumRepository,
        private val lessonsRepo: LessonRepository,
        private val bsuirApi: BsuirApi
) {


    /**
     * SEARCHING FREE AUDITORIUM
     * @param dateTime: Date - Date which contains Lesson DATE and TIME
     * @param building: Int - Building number
     * @param floor: Int - Floor number
     */
    fun search(dateTime: Date, building: Int, floor: Int? = null): List<Auditorium> {
        val allAuds: Set<Auditorium> = audRepo.find(
                building = building,
                floor = floor
        ).toSet()

        val currWeek = bsuirApi.getWeekNumber(dateTime)

        val calCurr = Calendar.getInstance()
        calCurr.time = dateTime
        val dayOfWeek = (calCurr.get(Calendar.DAY_OF_WEEK) - 1 + 6) % 7 // Monday - 0 day

        val busyAuds: Set<Auditorium> = lessonsRepo.find(
            day = dayOfWeek,
            time = Time(dateTime.time),
            building = building,
            floor = floor,
            weeks = Weeks(listOf(WeekNumber.values()[currWeek]))
        ).map { it.aud }.toSet()


        return (allAuds - busyAuds).sortedBy { it.name }
    }
}
