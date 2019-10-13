package men.brakh.bsuirapi.servlets.api.v1.users

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet

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