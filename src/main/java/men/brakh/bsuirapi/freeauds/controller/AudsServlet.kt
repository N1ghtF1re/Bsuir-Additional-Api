package men.brakh.bsuirapi.freeauds.controller

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AudsServlet : HttpServlet() {
    val lessonRep: LessonRepository = Config.lessonsRepository
    val audRep: AuditoriumRepository = Config.auditoriumRepository

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {

        /*val aud = Auditorium(
                name = "Test",
                type = LessonType.LESSON_LAB,
                floor = 1,
                building = 2
        )

        audRep.add(aud)*/

        resp.writer.println(audRep.findById(3))

/*
        val lesson = Lesson(
                aud = aud,
                weeks = Weeks(arrayOf(WeekNumber.WEEK_FIRST, WeekNumber.WEEK_SECOND)),
                startTime = Time(Date().time),
                endTime = Time(Date().time),
                group = "751006"
        )
*/
        //lessonRep.add(lesson)

        /*val lessons = lessonRep.find(
                building = 4
        )

        resp.writer.println(lessons)*/

        /*resp.writer.println(lessonRep.findByWeek(Weeks(arrayOf(WeekNumber.WEEK_SECOND))))
        resp.writer.println(lessonRep.findByWeek(Weeks(arrayOf(WeekNumber.WEEK_FIRST))))
        resp.writer.println(lessonRep.findByWeek(Weeks(arrayOf(WeekNumber.WEEK_FIRST, WeekNumber.WEEK_FOURTH))))
        resp.writer.println(lessonRep.findByWeek(Weeks(arrayOf(WeekNumber.WEEK_ANY))))*/

    }
}