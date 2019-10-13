package men.brakh.bsuirapi.controller.news

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.*
import men.brakh.bsuirapicore.model.data.News
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class NewsServlet : HttpServletWithErrorHandling(), JsonServlet {
    companion object {
        private val newsRepo = Config.newsRepository
        private val srcRepo = Config.newsSourceRepository
        private val tokenRepo = Config.tokenRepository
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {

        val token = req.extractToken(tokenRepo) ?: return resp.writeError("Authorization token is required", 403)


        val body: News =  req.extractBody(News::class.java)

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

        if(news.count() != 0) return resp.writeError("This news already exist", HttpServletResponse.SC_CONFLICT)


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
     * @param loadedAfter - minimum loading date (unix timestamp)
     * @param loadingBefore - maximum loading data (unix timestamp)
     * @param publishedAfter - minimum publication date (unix timestamp)
     * @param publishedBefore - maximum publication date (unix timestamp)
     * @param sources - list of sources id. Example: sources=1,2,3
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val params: Map<String, String> = req.singleParameters

        if("id" !in params) {
            resp.writeError("id parameter is required")
        }

        val news = newsRepo.findById(params["id"]!!.toLong())
        news?.let { resp.writeJson(it) } ?: resp.writeError("News not found", 404)
        return
    }
}