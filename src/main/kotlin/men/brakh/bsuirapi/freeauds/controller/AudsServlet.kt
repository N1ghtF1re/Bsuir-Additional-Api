package men.brakh.bsuirapi.freeauds.controller

import men.brakh.bsuirapi.extentions.setDefaultJsonHeaders
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.toJson
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.model.data.LessonType
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AudsServlet : HttpServlet() {
    private val audsRepository: AuditoriumRepository = Config.auditoriumRepository

    /**
     * PARAMS:
     * @name - Auditorium name (WITHOUT BUILDING NAME)
     * @building - Building number
     * @floor - Floor
     * @type - Auditorium type
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters

        val floor: Int? = params["floor"]?.toIntOrNull()
        val building: Int? = params["building"]?.toIntOrNull()
        val name: String? = params["name"]
        val type: LessonType?

        try {
            type = if ("type" in params) LessonType.valueOf(params.getValue("type")) else null
        } catch (e: IllegalArgumentException) {
            resp.writeError("Invalid type. Allowed values: ${LessonType.values().joinToString()}")
            return
        }

        val auds = audsRepository.find(
                name= name,
                building = building,
                floor = floor,
                type = type
        ).sortedWith(compareBy({ it.building }, { it.name }))

        resp.writer.write(auds.toJson())
    }
}
