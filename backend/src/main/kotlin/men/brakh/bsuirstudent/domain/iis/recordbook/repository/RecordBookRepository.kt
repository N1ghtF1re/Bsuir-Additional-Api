package men.brakh.bsuirstudent.domain.iis.recordbook.repository

import men.brakh.bsuirstudent.domain.iis.recordbook.RecordBook
import org.springframework.data.jpa.repository.JpaRepository

interface RecordBookRepository : JpaRepository<RecordBook, Int> {
    fun existsByNumber(number: String): Boolean
    fun findOneByNumber(number: String): RecordBook?
}