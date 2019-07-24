package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapicore.model.data.LessonType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AudsServlet : HttpServletWithErrorHandling(), JsonServlet {
    private val audsRepository: AuditoriumRepository = Config.auditoriumRepository

    /**
     * PARAMS:
     * @name - Auditorium name (WITHOUT BUILDING NAME)
     * @building - Building number
     * @floor - Floor
     * @type - Auditorium type
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
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

        resp.writeJson(auds)
    }
}
