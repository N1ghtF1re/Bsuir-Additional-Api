package men.brakh.bsuirapi.controller

import com.google.gson.JsonParseException
import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.extentions.*
import men.brakh.bsuirapi.model.data.News
import men.brakh.bsuirapi.model.dto.NewsListDto
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class NewsServlet : HttpServlet() {
    private val newsRepo = Config.newsRepository
    private val srcRepo = Config.newsSourceRepository
    private val tokenRepo = Config.tokenRepository

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()
        val token = req.extractToken(tokenRepo)
        if(token == null) {
            resp.writeError("Authorization token is required", 403)
            return
        }



        val body: News =  try {
            req.extractBody(News::class.java)
        } catch (e: JsonParseException) {
            e.printStackTrace()
            resp.writeError("Invalid json")
            return
        }

        val src =
                srcRepo.find(type = body.source.type, name = body.source.name).firstOrNull() ?: srcRepo.add(body.source)

        val news =
            newsRepo.find(
                    title = body.title,
                    source = srcRepo.find(name = body.source.name, type = body.source.type).firstOrNull() ?: body.source,
                    url = body.url,
                    urlToImage = body.urlToImage,
                    page = 1,
                    newsAtPage = 1
            )
        if(news.count() != 0) {
            resp.writeError("This news already exist", HttpServletResponse.SC_CONFLICT)
            return
        }


        newsRepo.add(body.copy(source = src))
        resp.status = HttpServletResponse.SC_CREATED
    }

    /**
     * PARAMS:
     * @param id - news id
     * @param page - page number
     * @param newsAtPage - news count at one page
     * @param title - news title
     * @param content - news content
     * @param url - news url
     * @param source - id of news source (@see SourcesServlet)
     * @param loadedAfter - minimum loading date
     * @param loadingBefore - maximum loading data
     * @param publishedAfter - minimum publication date
     * @param publishedBefore - maximum publication date
     * @param sources - list of sources id. Example: sources=1,2,3
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters
        val multiParams: Map<String, Array<String>> = req.parameterMap

        if("id" in params) {
            val news = newsRepo.findById(params["id"]!!.toLong())
            news?.let { resp.writeJson(it) } ?: resp.writeError("News not found", 404)
            return
        }

        val format = SimpleDateFormat(Config.dateFormat)

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

        resp.writeJson(newsListDto)
    }
}