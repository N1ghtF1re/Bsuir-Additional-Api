package men.brakh.bsuirapi.servlets.api.v1.users

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet

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