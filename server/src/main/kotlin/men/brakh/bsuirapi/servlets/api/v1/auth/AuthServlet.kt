package men.brakh.bsuirapi.servlets.api.v1.auth

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet

class AuthServlet: BasicHttpServlet() {
    private val userService = Config.userService

    override fun handle() {
        get { parameters ->
            val token = userService.getTokenForUser(parameters.getRequired("login"),
                    parameters.getRequired("password"))

            mapOf("token" to token)
        }
    }
}
