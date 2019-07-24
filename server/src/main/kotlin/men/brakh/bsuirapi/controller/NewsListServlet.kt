package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.extentions.setDefaultJsonHeaders
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.dto.NewsListDto
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NewsListServlet : HttpServletWithErrorHandling() {
    private val newsRepo = Config.newsRepository
    private val srcRepo = Config.newsSourceRepository

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
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters
        val multiParams: Map<String, Array<String>> = req.parameterMap


        val page = params["page"]?.toIntOrNull() ?: 1
        val newsAtPage = params["newsAtPage"]?.toIntOrNull() ?: Config.newsAtPage

        val sourcesIds = if("sources" in params && "," in params["sources"]!!) {
            params["sources"]!!.split(",")
        } else {
            multiParams["sources"]?.toList()
        }

        val sources = sourcesIds
                ?.asSequence()
                ?.mapNotNull { s: String -> s.toLongOrNull() }
                ?.mapNotNull { id: Long -> srcRepo.findById(id) }
                ?.toList()

        val news = if(sources?.count() != 0) {
            newsRepo.find(
                    title = params["title"],
                    contentLike = params["q"],
                    urlToImage = params["urlToImage"],
                    url = params["url"],
                    loadedAfter = params["loadedAfter"]?.toIntOrNull()?.toDate(),
                    loadedBefore = params["loadedBefore"]?.toIntOrNull()?.toDate(),
                    publishedAfter = params["publishedAfter"]?.toIntOrNull()?.toDate(),
                    publishedBefore = params["publishedBefore"]?.toIntOrNull()?.toDate(),
                    sources = sources,
                    page = page,
                    newsAtPage = newsAtPage
            )
        } else {
            listOf()
        }

        val newsListDto = NewsListDto(newsAtPage = newsAtPage, page = page, count = news.count(), news = news)

        resp.writeJson(newsListDto)
    }
}