package men.brakh.bsuirapi.servlets.api.v1.auds

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import men.brakh.bsuirapicore.model.data.LessonType

class AudsServlet : BasicHttpServlet() {
    private val audsRepository: AuditoriumRepository = Config.auditoriumRepository

    override fun handle() {
        /**
         * PARAMS:
         * @name - Auditorium name (WITHOUT BUILDING NAME)
         * @building - Building number
         * @floor - Floor
         * @type - Auditorium type
         */
        get { parameters ->
            val floor: Int? = parameters["floor"]?.toIntOrNull()
            val building: Int? = parameters["building"]?.toIntOrNull()
            val name: String? = parameters["name"]
            val type: LessonType?

            try {
                type = if ("type" in parameters) LessonType.valueOf(parameters["type"]!!) else null
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid type. Allowed values: ${LessonType.values().joinToString()}")
            }

           audsRepository.find(
                    name= name,
                    building = building,
                    floor = floor,
                    type = type
            ).sortedWith(compareBy({ it.building }, { it.name }))
        }
    }


}
