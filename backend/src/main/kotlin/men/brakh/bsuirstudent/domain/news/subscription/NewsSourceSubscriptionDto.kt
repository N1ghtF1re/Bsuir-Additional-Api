package men.brakh.bsuirstudent.domain.news.subscription

import men.brakh.bsuirstudent.domain.UpdateDto

data class NewsSourceSubscriptionRequest(
    val newsSourcesAliases: List<String>
): UpdateDto