package men.brakh.bsuirstudent.domain.news.subscription.serivce

import men.brakh.bsuirstudent.domain.news.subscription.NewsSourceSubscriptionRequest

interface NewsSourceSubscriptionService {
    fun getSubscriptions(): List<String>
    fun updateSubscriptions(request: NewsSourceSubscriptionRequest)
}