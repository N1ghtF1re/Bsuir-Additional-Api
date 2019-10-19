package men.brakh.bsuirapi.servlets.api.v1.users

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/users/*", loadOnStartup = 1)
class UserInfoServlet: BasicHttpServlet() {
    companion object {
        private val userService = Config.userService
    }

    override fun handle() {
        get {
            urlHandling { path ->
                val id = path.split("/").last().toIntOrNull() ?: throw IllegalArgumentException()

                userService.getUserById(id)
            }
        }
    }
}