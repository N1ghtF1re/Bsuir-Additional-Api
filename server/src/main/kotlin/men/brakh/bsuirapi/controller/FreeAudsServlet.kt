package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.FreeAuds
import men.brakh.bsuirapicore.model.data.Auditorium
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



class FreeAudsServlet : HttpServletWithErrorHandling(), JsonServlet {

    /**
     * PARAMS:
     * @param building [require] - Building number (1,2,3,4,5)
     * @param floor - Floor number
     * @param date - Date in format "dd.MM.yyyy". Default: Today date
     * @param time - Lesson time in format "HH:mm" (Any time between start and end of lesson). Default: now
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val params: Map<String, String> = req.singleParameters

        val building: Int = params["building"]?.toIntOrNull() ?: return resp.writeError("Building parameter is required")


        val floor: Int? = params["floor"]?.toIntOrNull()
        val dateStr: String = params["date"] ?: SimpleDateFormat("dd.MM.yyyy").format(Date())
        val timeStr: String = params["time"] ?: SimpleDateFormat("HH:mm").format(Date())

        val dateTime: Date = SimpleDateFormat("dd.MM.yyyy HH:mm").parse("$dateStr $timeStr")

        val freeAuds: List<Auditorium> = FreeAuds.search(
                dateTime = dateTime,
                floor = floor,
                building = building
        ).sortedBy { it.name }

        resp.writeJson(freeAuds)
    }
}