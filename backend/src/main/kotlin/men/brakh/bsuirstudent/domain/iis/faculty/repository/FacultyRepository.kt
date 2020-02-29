package men.brakh.bsuirstudent.domain.iis.faculty.repository

import men.brakh.bsuirstudent.domain.iis.faculty.Faculty
import org.springframework.data.jpa.repository.JpaRepository

interface FacultyRepository : JpaRepository<Faculty, Int> {
}