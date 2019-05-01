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
        val params: Map<String, String> =
                req.parameterMap.mapValues { (_, value) -> value.first() }



    }
}