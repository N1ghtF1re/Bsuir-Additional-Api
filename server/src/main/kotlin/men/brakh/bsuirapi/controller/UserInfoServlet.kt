package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserInfoServlet: HttpServletWithErrorHandling(), JsonServlet {
    private val bsuirApi = BsuirApi

    /**
     * @param id - Id in path
     */
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val path: String? = req.pathInfo

        val id = path?.substring("/".length)?.toIntOrNull() ?: throw IllegalArgumentException()

        resp.writeJson(bsuirApi.getUserCV(id).toUserInfoObject())
    }
}