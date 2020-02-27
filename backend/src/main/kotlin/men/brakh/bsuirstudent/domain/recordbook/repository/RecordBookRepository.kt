package men.brakh.bsuirstudent.domain.recordbook.repository

import men.brakh.bsuirstudent.domain.recordbook.RecordBook
import org.springframework.data.jpa.repository.JpaRepository

interface RecordBookRepository : JpaRepository<RecordBook, Int> {
    fun existsByNumber(number: String): Boolean
    fun findOneByNumber(number: String): RecordBook?
}