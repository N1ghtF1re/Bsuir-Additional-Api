package men.brakh.bsuirapi.app.news.service.impl

import men.brakh.bsuirapi.EntityAlreadyExistsException
import men.brakh.bsuirapi.NotFoundException
import men.brakh.bsuirapi.app.news.service.NewsService
import men.brakh.bsuirapi.repository.NewsRepository
import men.brakh.bsuirapi.repository.NewsSourceRepository
import men.brakh.bsuirapicore.model.data.News
import men.brakh.bsuirapicore.model.data.NewsSource
import java.util.*

class NewsServiceImpl(private val newsRepository: NewsRepository,
                      private val newsSourceRepository: NewsSourceRepository) : NewsService {

    private val urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"

    override fun getSourceById(id: Long): NewsSource? {
        return newsSourceRepository.findById(id)
    }

    override fun getSources(type: String?): List<NewsSource> {
        return if(type == null) {
            newsSourceRepository.findAll()
        } else {
            newsSourceRepository.find(type = type)
        }
    }


    override fun getNewsById(id: Long): News {
        val news = newsRepository.findById(id)
        return news ?: throw NotFoundException("News is not found")
    }

    override fun createNews(news: News): News {
        val src = newsSourceRepository.find(type = news.source.type, name = news.source.name).firstOrNull()
            ?: newsSourceRepository.add(news.source) // Create source if source isn't exist

        val newsList =
                newsRepository.find(
                        title = news.title,
                        source = newsSourceRepository.find(
                                name = news.source.name,
                                type = news.source.type).firstOrNull() ?: news.source,
                        url = news.url,
                        urlToImage = news.urlToImage,
                        page = 1,
                        newsAtPage = 1
                )

        if(newsList.count() != 0) throw EntityAlreadyExistsException()


        return newsRepository.add(news.copy(source = src, shortContent = truncateContent(news.content)))
    }

    private fun truncateContent(content: String): String {
        val withoutImages = content
                .replace("!\\[[^\\]]+\\]\\([^\\)]+\\)".toRegex(), "")
                .replace("\\[[^\\]]+\\]: $urlRegex".toRegex(), "")

        return if (withoutImages.length < 255) {
            withoutImages
        } else {
            withoutImages.substring(0..255) + "..."
        }
    }

    override fun getNewsList(title: String?,
                             source: NewsSource?,
                             sources: List<NewsSource>?,
                             contentLike: String?,
                             publishedAfter: Date?,
                             publishedBefore: Date?,
                             loadedAfter: Date?,
                             loadedBefore: Date?,
                             url: String?,
                             urlToImage: String?,
                             page: Int?,
                             newsAtPage: Int?): List<News> {
        return newsRepository.find(
                title = title,
                source = source,
                sources = sources,
                contentLike = contentLike,
                publishedAfter = publishedAfter,
                publishedBefore = publishedBefore,
                loadedAfter = loadedAfter,
                loadedBefore = loadedBefore,
                url = url,
                urlToImage = urlToImage,
                page = page,
                newsAtPage = newsAtPage
        )
    }
}