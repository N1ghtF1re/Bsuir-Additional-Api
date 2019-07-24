package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.authorized
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DiplomaServlet: HttpServletWithErrorHandling(), JsonServlet {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        authorized(req) { user ->
            resp.writeJson(BsuirApi.getDiploma(user).toDiplomaInfoObject())
        }
    }
}