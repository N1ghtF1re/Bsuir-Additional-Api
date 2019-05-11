package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.extentions.setDefaultJsonHeaders
import men.brakh.bsuirapi.extentions.singleParameters
import men.brakh.bsuirapi.extentions.toJson
import men.brakh.bsuirapi.extentions.writeError
import men.brakh.bsuirapi.model.dto.NewsListDto
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NewsServlet : HttpServlet() {
    private val newsRepo = Config.newsRepository
    private val srcRepo = Config.newsSourceRepository

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        TODO()
    }

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters
        val multiParams: Map<String, Array<String>> = req.parameterMap

        if("id" in params) {
            resp.writer.write(newsRepo.findById(params["id"]!!.toLong())?.toJson())
            return
        }

        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")

        val page = params["page"]?.toIntOrNull() ?: 1
        val newsAtPage = params["newsAtPage"]?.toIntOrNull() ?: Config.newsAtPage

        val news = try {
            newsRepo.find(
                    title = params["title"],
                    content = params["content"],
                    urlToImage = params["urlToImage"],
                    url = params["url"],
                    source = params["source"]
                            ?.toLongOrNull()
                            ?.let {
                                val src = srcRepo.findById(it)
                                if (src == null) {
                                    resp.writeError("News source not found")
                                    return
                                }
                                src
                            },
                    loadedAfter = params["loadedAfter"]?.let { format.parse(it) },
                    loadedBefore = params["loadedBefore"]?.let { format.parse(it) },
                    publishedAfter = params["publishedAfter"]?.let { format.parse(it) },
                    publishedBefore = params["publishedBefore"]?.let { format.parse(it) },
                    sources = multiParams["sources"]
                            ?.asSequence()
                            ?.mapNotNull { s: String -> s.toLongOrNull() }
                            ?.mapNotNull { id: Long -> srcRepo.findById(id) }
                            ?.toList(),
                    page = page,
                    newsAtPage = newsAtPage
            )
        } catch (e: ParseException) {
            resp.writeError("Invalid date format")
            return
        }

        val newsListDto = NewsListDto(
                newsAtPage = newsAtPage,
                page = page,
                count = news.count(),
                news = news
        )

        resp.writer.write(newsListDto.toJson())
    }
}