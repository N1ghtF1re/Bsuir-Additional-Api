package men.brakh.bsuirstudent.domain.news

import org.springframework.data.jpa.repository.JpaRepository

interface NewsSourceRepository : JpaRepository<NewsSource, Int> {
    fun findOneByNameAndType(name: String, type: String): NewsSource?
    fun findOneByAlias(alias: String): NewsSource?
}