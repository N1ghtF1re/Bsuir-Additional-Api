package men.brakh.bsuirapi.freeauds.repository

import men.brakh.bsuirapi.freeauds.model.Auditorium

interface AuditoriumRepository {
    fun findByBuilding(building: Int): List<Auditorium>
    fun findByBuildingAndFloor(building: Int, floor: Int): List<Auditorium>
}