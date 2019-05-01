package men.brakh.bsuirapi.freeauds.controller

import com.google.gson.Gson
import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.FreeAuds
import men.brakh.bsuirapi.freeauds.model.LessonType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FreeAudsServlet : HttpServlet() {

    /**
     * PARAMS:
     * @param building [require] - Building number (1,2,3,4,5)
     * @param floor - Floor number
     * @param date - Date in format "dd.MM.yyyy". Default: Today date
     * @param time - Lesson time in format "HH:mm" (Any time between start and end of lesson). Default: now
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters

        val building: Int? = params["building"]?.toIntOrNull()

        if(building == null) {
            resp.writeError("Building parameter is required")
            return
        }

        val floor: Int? = params["floor"]?.toIntOrNull()
        val dateStr: String = params["date"] ?: SimpleDateFormat("dd.MM.yyyy").format(Date())
        val timeStr: String = params["time"] ?: SimpleDateFormat("HH:mm").format(Date())

        val dateTime: Date =
                try {
                    SimpleDateFormat("dd.MM.yyyy HH:mm").parse("$dateStr $timeStr")
                } catch (e: ParseException) {
                    resp.writeError("Invalid date/time format")
                    return
                }

        val freeAuds: Map<LessonType, List<Auditorium>> = FreeAuds.search(
                dateTime = dateTime,
                floor = floor,
                building = building
        ).groupBy { auditorium -> auditorium.type }
         .mapValues { (_, value) -> value.sortedBy { auditorium ->  auditorium.name} }

        val json: String = Gson().toJson(freeAuds)
        resp.writer.write(json)
    }
}