package men.brakh.bsuirapi.servlets.api.v1.users

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/user", loadOnStartup = 1)
class CurrentUserInfoServlet: BasicHttpServlet() {
    companion object {
        private val userService = Config.userService
    }

    override fun handle() {
        get {
            authorized {
                userService.getAuthenticatedUser()
            }
        }
    }
}