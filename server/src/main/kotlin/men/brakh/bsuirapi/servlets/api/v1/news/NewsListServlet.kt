package men.brakh.bsuirapi.servlets.api.v1.news

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.app.news.dto.NewsListDto
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import java.util.*

class NewsListServlet : BasicHttpServlet() {
    companion object {
        private val newsService = Config.newsService
    }

    /**
     * Unix timestamp to date
     */
    private fun Int.toDate() = Date(this * 1000L)

    /**
     * PARAMS:
     * @param page - page number
     * @param newsAtPage - news count at one page
     * @param title - news title
     * @param content - news content
     * @param url - news url
     * @param source - id of news source (@see SourcesServlet)
     * @param loadedAfter - minimum loading date (unix timestamp)
     * @param loadingBefore - maximum loading data (unix timestamp)
     * @param publishedAfter - minimum publication date (unix timestamp)
     * @param publishedBefore - maximum publication date (unix timestamp)
     * @param sources - list of sources id. Example: sources=1,2,3
     *
     * todo: refactor it
     */
    override fun handle() {
        get<NewsListDto> { parameters ->
            multipleParameters { multipleParameters ->
                val page = parameters["page"]?.toIntOrNull() ?: 1
                val newsAtPage = parameters["newsAtPage"]?.toIntOrNull() ?: Config.newsAtPage

                val sourcesIds = if ("sources" in parameters && "," in parameters["sources"]!!) {
                    parameters["sources"]!!.split(",")
                } else {
                    multipleParameters["sources"]?.toList()
                }

                val sources = sourcesIds
                        ?.asSequence()
                        ?.mapNotNull { s: String -> s.toLongOrNull() }
                        ?.mapNotNull { id: Long -> newsService.getSourceById(id) }
                        ?.toList()

                val news = if (sources?.count() != 0) {
                    newsService.getNewsList(
                            title = parameters["title"],
                            contentLike = parameters["q"],
                            urlToImage = parameters["urlToImage"],
                            url = parameters["url"],
                            loadedAfter = parameters["loadedAfter"]?.toIntOrNull()?.toDate(),
                            loadedBefore = parameters["loadedBefore"]?.toIntOrNull()?.toDate(),
                            publishedAfter = parameters["publishedAfter"]?.toIntOrNull()?.toDate(),
                            publishedBefore = parameters["publishedBefore"]?.toIntOrNull()?.toDate(),
                            sources = sources,
                            page = page,
                            newsAtPage = newsAtPage
                    )
                } else {
                    listOf()
                }

                NewsListDto(newsAtPage = newsAtPage, page = page, count = news.count(), news = news)
            }
        }
    }

}