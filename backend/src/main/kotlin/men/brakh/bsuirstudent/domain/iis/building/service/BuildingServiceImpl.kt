package men.brakh.bsuirstudent.domain.iis.building.service

import men.brakh.bsuirstudent.domain.iis.auditoriums.repository.AuditoriumRepository
import men.brakh.bsuirstudent.domain.iis.building.Building
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
@RequestMapping(path = ["/api/v2/buildings"])
class BuildingServiceImpl(
    private val auditoriumRepository: AuditoriumRepository
) : BuildingService {

    private companion object {
        var buildings: List<Building> = listOf();
    }

    @GetMapping
    @ResponseBody
    override fun getBuildings(): List<Building> {
        if (buildings.isEmpty()) {
            synchronized(this) {
                if (buildings.isEmpty()) {
                    buildings = auditoriumRepository.findAll()
                        .groupBy { it.building }
                        .map { (buildingNumber, auds) ->

                            val floors: List<Int> = auds.groupBy { aud -> aud.floor }
                                .keys
                                .toList()
                                .sorted()

                            Building(buildingNumber, floors)
                        }.sortedBy { it.name }
                }
            }
        }

        return buildings
    }

}