package men.brakh.bsuirapi.servlets.api.v1.education

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet

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