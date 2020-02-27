package men.brakh.bsuirstudent.domain.news.repository

import men.brakh.bsuirstudent.domain.news.NewsSource
import org.springframework.data.jpa.repository.JpaRepository

interface NewsSourceRepository : JpaRepository<NewsSource, Int> {
    fun findOneByNameAndType(name: String, type: String): NewsSource?
    fun findOneByAlias(alias: String): NewsSource?
}