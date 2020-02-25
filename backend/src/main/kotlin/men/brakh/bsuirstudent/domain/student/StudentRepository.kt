package men.brakh.bsuirstudent.domain.student

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Int> {
    fun existsByRecordBookNumber(recordBookNumber: String): Boolean
    fun findOneByRecordBookNumber(recordBookNumber: String): Student?
}