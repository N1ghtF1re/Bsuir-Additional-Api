package men.brakh.bsuirapi.servlets.api.v1.users

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import men.brakh.bsuirapicore.model.data.UserSettings
import javax.servlet.annotation.WebServlet

@WebServlet("/api/v1/user/settings", loadOnStartup = 1)
class CurrentUserSettingsServlet: BasicHttpServlet() {
    companion object {
        private val userService = Config.userService
    }


    override fun handle() {
        get {
            authorized {
                userService.getCurrentUserSettings()
            }
        }

        put<UserSettings, Unit> { body ->
            authorized {
                userService.changeUserSettings(body)
            }
        }
    }
}