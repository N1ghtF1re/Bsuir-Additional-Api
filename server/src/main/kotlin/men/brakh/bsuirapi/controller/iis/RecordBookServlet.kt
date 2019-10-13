package men.brakh.bsuirapi.controller.iis

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.app.authorization.authorized
import men.brakh.bsuirapi.app.bsuirapi.BsuirApi
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RecordBookServlet: HttpServletWithErrorHandling(), JsonServlet {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        authorized(req) { user ->
            resp.writeJson(BsuirApi.getMarkBook(user).toRecordBookObject())
        }
    }
}