package men.brakh.bsuirapi.repository

import men.brakh.bsuirapicore.model.data.News
import men.brakh.bsuirapicore.model.data.NewsSource
import java.util.*

interface NewsRepository : Repository<News> {
    fun find(
            title: String? = null,
            source: NewsSource? = null,
            sources: List<NewsSource>? = null,
            contentLike: String? = null,
            publishedAfter: Date? = null,
            publishedBefore: Date? = null,
            loadedAfter: Date? = null,
            loadedBefore: Date? = null,
            url: String? = null,
            urlToImage: String? = null,
            page: Int?,
            newsAtPage: Int?
    ) : List<News>
}