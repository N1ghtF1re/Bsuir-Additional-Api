package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.extentions.setDefaultJsonHeaders
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserInfoServlet: HttpServletWithErrorHandling() {
    private val bsuirApi = BsuirApi
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.setDefaultJsonHeaders()

        val path = req.pathInfo

        val id = path.split("/").last().toIntOrNull() ?: throw IllegalArgumentException()

        resp.writeJson(bsuirApi.getUserInfo(id))
    }
}