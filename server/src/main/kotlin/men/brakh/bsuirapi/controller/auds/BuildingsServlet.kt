package men.brakh.bsuirapi.controller.auds

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapicore.model.data.Building
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class BuildingsServlet : HttpServletWithErrorHandling(), JsonServlet {
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

        resp.writeJson(buildings)
    }
}