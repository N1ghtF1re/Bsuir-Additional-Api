package men.brakh.bsuirapi.servlets.api.v1.auds

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import java.text.SimpleDateFormat
import java.util.*



class FreeAudsServlet : BasicHttpServlet(){
    val freeAudsService = Config.freeAudsService

    override fun handle() {
        get { parameters ->
            val building: Int = parameters.getRequired("building").toIntOrNull()
                    ?: throw IllegalArgumentException("Invalid value 'building'")


            val floor: Int? = parameters["floor"]?.toIntOrNull()
            val dateStr: String = parameters["date"] ?: SimpleDateFormat("dd.MM.yyyy").format(Date())
            val timeStr: String = parameters["time"] ?: SimpleDateFormat("HH:mm").format(Date())

            val dateTime: Date = SimpleDateFormat("dd.MM.yyyy HH:mm").parse("$dateStr $timeStr")

            freeAudsService.search(
                    dateTime = dateTime,
                    floor = floor,
                    building = building
            )
        }
    }
}