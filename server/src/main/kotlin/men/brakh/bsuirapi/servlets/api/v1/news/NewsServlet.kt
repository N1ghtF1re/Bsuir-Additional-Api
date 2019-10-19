package men.brakh.bsuirapi.servlets.api.v1.news

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.inrfastructure.authorization.Permission
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import men.brakh.bsuirapicore.model.data.News
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletResponse

@WebServlet("/api/v1/news", loadOnStartup = 1)
class NewsServlet : BasicHttpServlet() {
    companion object {
        private val newsService = Config.newsService
    }

    override fun handle() {
        get { parameters ->
            newsService.getNewsById(parameters.getRequired("id").toLong())
        }

        post<News, News> { body ->
            authorized {
                hasPermission(Permission.CREATE_NEWS) {
                    response { response ->
                        response.status = HttpServletResponse.SC_CREATED
                        newsService.createNews(body)
                    }
                }
            }
        }
    }

}