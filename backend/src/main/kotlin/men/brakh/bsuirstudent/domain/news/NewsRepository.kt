package men.brakh.bsuirstudent.domain.news

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface NewsRepository : JpaRepository<News, Int>, JpaSpecificationExecutor<News>