package men.brakh.bsuirstudent.domain.iis.auditoriums.service

import men.brakh.bsuirstudent.application.bsuir.BsuirScheduleService
import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import men.brakh.bsuirstudent.domain.iis.auditoriums.AuditoriumDto
import men.brakh.bsuirstudent.domain.iis.auditoriums.mapping.AuditoriumPresenter
import men.brakh.bsuirstudent.domain.iis.auditoriums.repository.AuditoriumRepository
import men.brakh.bsuirstudent.domain.iis.lesson.Lesson
import men.brakh.bsuirstudent.domain.iis.lesson.repository.LessonRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.criteria.Join
import javax.persistence.criteria.ListJoin


@Service
@RestController
@RequestMapping(path = ["/api/v2/auditoriums/free"])
class FreeAuditoriumsServiceImpl(
    private val audsRepository: AuditoriumRepository,
    private val lessonsRepository: LessonRepository,
    private val bsuirService: BsuirScheduleService,
    private val auditoriumPresenter: AuditoriumPresenter
) : FreeAuditoriumService {

    @GetMapping
    @ResponseBody
    override fun getFreeAuditoriums(
        @RequestParam(name = "building", required = true) building: Int,
        @RequestParam(name = "floor", required = false) floor: Int?,
        @RequestParam(name = "time", required = true) time: String?,
        @RequestParam(name = "date", required = true) date: String?
    ): List<AuditoriumDto> {

        val dateTime: Date = SimpleDateFormat("yyyy-MM-dd HH:mm").parse("$date $time")

        return auditoriumPresenter.mapListToDto(
            search(
                building = building,
                dateTime = dateTime,
                floor = floor
            ),
            AuditoriumDto::class.java
        )
    }


    private fun getDayOfWeek(date: Date): Int {
        val calCurr = Calendar.getInstance()
        calCurr.time = date
        return (calCurr.get(Calendar.DAY_OF_WEEK) - 1 + 6) % 7 // Monday - 0 day
    }

    private fun search(dateTime: Date, building: Int, floor: Int? = null): List<Auditorium> {
        val allAuds: Set<Auditorium> = if (floor == null)
            audsRepository.findAllByBuilding(building)
        else
            audsRepository.findAllByBuildingAndFloor(building, floor)

        val currWeek = bsuirService.getWeekNumber(dateTime)
        val dayOfWeek = getDayOfWeek(dateTime)

        val busyAuds = getBusyAuds(dayOfWeek, dateTime, building, floor, currWeek).map { it.aud }

        return (allAuds - busyAuds).sortedBy { it.name }

    }

    private fun getBusyAuds(
        dayOfWeek: Int,
        dateTime: Date,
        building: Int,
        floor: Int?,
        currWeek: Int
    ): List<Lesson> {
        return lessonsRepository.findAll { root, cq, cb ->
            val weeks: ListJoin<Lesson, Int> = root.joinList<Lesson, Int>("weeks")
            val audsJoin: Join<Lesson, Auditorium> = root.join<Lesson, Auditorium>("aud")
            cb.and(
                cb.equal(root.get<Int>("day"), dayOfWeek),
                cb.greaterThan(root.get<Time>("endTime"), Time(dateTime.time)),
                cb.lessThan(root.get<Time>("startTime"), Time(dateTime.time)),
                cb.equal(audsJoin.get<Int>("building"), building),
                if (floor != null) cb.equal(audsJoin.get<Int>("floor"), floor) else cb.isNotNull(audsJoin.get<Int>("floor")),
                cb.equal(weeks, currWeek)
            )
        }
    }


}