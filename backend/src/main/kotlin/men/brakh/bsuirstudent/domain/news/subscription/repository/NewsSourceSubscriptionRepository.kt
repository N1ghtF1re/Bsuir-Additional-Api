package men.brakh.bsuirstudent.domain.news.subscription.repository

import men.brakh.bsuirstudent.domain.news.subscription.NewsSourceSubscription
import org.springframework.data.jpa.repository.JpaRepository

interface NewsSourceSubscriptionRepository: JpaRepository<NewsSourceSubscription, Int> {
    fun findAllByEntityId(newsSourceId: Int): List<NewsSourceSubscription>
    fun findAllByUserId(userId: Int): List<NewsSourceSubscription>
    fun deleteAllByUserId(userId: Int)
}