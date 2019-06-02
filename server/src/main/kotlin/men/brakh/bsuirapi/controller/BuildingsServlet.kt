package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapicore.model.data.Building
import men.brakh.bsuirapi.repository.AuditoriumRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import men.brakh.bsuirapi.extentions.*


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

        resp.writeJson(buildings)
    }
}