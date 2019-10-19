package men.brakh.bsuirapi.servlets.api.v1.news

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/sources", loadOnStartup = 1)
class SourcesServlet : BasicHttpServlet() {
    companion object {
        private val newsService = Config.newsService
    }


    override fun handle() {
        get { parameters ->
            newsService.getSources(parameters["type"])
        }
    }

}