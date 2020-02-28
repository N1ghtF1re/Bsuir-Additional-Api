package men.brakh.bsuirstudent.domain.iis.auditoriums.repository

import men.brakh.bsuirstudent.domain.iis.auditoriums.Auditorium
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AuditoriumRepository : JpaRepository<Auditorium, Int>, JpaSpecificationExecutor<Auditorium> {

}