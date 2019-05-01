package men.brakh.bsuirapi.freeauds.controller

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.model.Building
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.extentions.setDefaultJsonHeaders
import men.brakh.extentions.toJson
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BuildingsServlet : HttpServlet() {
    private companion object {
        private val audsRepository: AuditoriumRepository = Config.auditoriumRepository
        val buildings: List<Building> = audsRepository.findAll()
                .groupBy { it.building }
                .map { (buildingNumber, auds) ->

                    val floors: List<Int> = auds.groupBy { aud -> aud.floor }
                            .keys
                            .toList()
                            .sorted()

                    Building(buildingNumber, floors)
                }.sortedBy { it.name }
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        resp.writer.write(buildings.toJson())
    }
}