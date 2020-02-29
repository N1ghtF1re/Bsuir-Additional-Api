package men.brakh.bsuirstudent.domain.iis.building.service

import men.brakh.bsuirstudent.domain.iis.building.Building

interface BuildingService {
    fun getBuildings(): List<Building>
}