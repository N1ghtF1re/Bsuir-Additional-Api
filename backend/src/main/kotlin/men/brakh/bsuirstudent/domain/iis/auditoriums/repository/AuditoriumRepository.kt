package men.brakh.bsuirstudent.domain.iis.auditoriums.repository

import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AuditoriumRepository : JpaRepository<Auditorium, Int>, JpaSpecificationExecutor<Auditorium> {
    fun findOneByBuildingAndFloorAndName(building: Int, flor: Int, name: String): Auditorium?
    fun findAllByBuildingAndFloor(building: Int, flor: Int): Set<Auditorium>
    fun findAllByBuilding(building: Int): Set<Auditorium>
}