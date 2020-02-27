package men.brakh.bsuirstudent.domain.iis.group.repository

import men.brakh.bsuirstudent.domain.iis.group.Group
import org.springframework.data.jpa.repository.JpaRepository


interface GroupRepository : JpaRepository<Group, Int> {
    fun existsByNumber(number: String): Boolean
    fun findOneByNumber(number: String): Group?
}