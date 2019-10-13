package men.brakh.bsuirapi.servlets.api.v1.auds

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import men.brakh.bsuirapi.servlets.basic.writeJson
import men.brakh.bsuirapicore.model.data.Building
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class BuildingsServlet : BasicHttpServlet() {
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
    override fun handle() {
        get {
            buildings
        }
    }
}