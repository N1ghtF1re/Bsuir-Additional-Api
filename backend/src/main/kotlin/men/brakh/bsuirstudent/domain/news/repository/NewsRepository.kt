package men.brakh.bsuirstudent.domain.news.repository

import men.brakh.bsuirstudent.domain.news.News
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface NewsRepository : JpaRepository<News, Int>, JpaSpecificationExecutor<News>