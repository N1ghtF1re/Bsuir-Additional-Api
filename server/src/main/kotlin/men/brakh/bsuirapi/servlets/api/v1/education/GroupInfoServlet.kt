package men.brakh.bsuirapi.servlets.api.v1.education

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.writeJson
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GroupInfoServlet : BasicHttpServlet() {
    companion object {
        private val educationService = Config.educationService
    }

    override fun handle() {
        get {
            authorized {
                educationService.getCurrentUserGroupInfo()
            }
        }
    }
}