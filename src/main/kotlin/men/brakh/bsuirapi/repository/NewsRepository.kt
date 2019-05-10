package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.model.data.News
import men.brakh.bsuirapi.model.data.NewsSource
import java.util.*

interface NewsRepository : Repository<News> {
    fun find(
            title: String? = null,
            source: NewsSource? = null,
            sources: List<NewsSource>? = null,
            content: String? = null,
            publishedAfter: Date? = null,
            publishedBefore: Date? = null,
            loadedAfter: Date? = null,
            loadedBefore: Date? = null,
            url: String? = null,
            urlToImage: String? = null
    ) : List<News>
}