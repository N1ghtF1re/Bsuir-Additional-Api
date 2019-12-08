package men.brakh.bsuirapi.servlets.api.v1.faculty

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/faculties", loadOnStartup = 1)
class FacultyServlet : BasicHttpServlet() {
    private val facultyService = Config.facultyService

    override fun handle() {
        get {
            facultyService.getFaculties()
        }
    }

}