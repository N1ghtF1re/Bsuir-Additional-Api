package men.brakh.bsuirapi.servlets.api.v1.education

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/user/record-book/diploma", loadOnStartup = 1)
class DiplomaServlet: BasicHttpServlet() {
    companion object {
        private val educationService = Config.educationService
    }

    override fun handle() {
        get {
            authorized {
                educationService.getCurrentUserDimploma()
            }
        }
    }
}