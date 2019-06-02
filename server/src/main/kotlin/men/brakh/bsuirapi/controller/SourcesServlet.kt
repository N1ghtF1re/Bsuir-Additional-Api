package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.Config
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import men.brakh.bsuirapi.extentions.*

class SourcesServlet : HttpServlet() {
    private val srcRepo = Config.newsSourceRepository

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val params: Map<String, String> = req.singleParameters

        val type = params["type"]

        val sources = if(type == null) {
            srcRepo.findAll()
        } else {
            srcRepo.find(type = type)
        }

        resp.writeJson(sources)
    }
}