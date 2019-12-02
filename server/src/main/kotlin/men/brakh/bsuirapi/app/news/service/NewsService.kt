package men.brakh.bsuirapi.app.news.service

import men.brakh.bsuirapi.app.news.dto.NewsListDto
import men.brakh.bsuirapicore.model.data.News
import men.brakh.bsuirapicore.model.data.NewsSource
import java.util.*

interface NewsService {
    fun getNewsById(id: Long): News
    fun createNews(news: News): News
    fun getSources(type: String?): List<NewsSource>
    fun getSourceById(id: Long): NewsSource?
    fun getNewsList(
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
            page: Int,
            newsAtPage: Int
    ) : NewsListDto
}