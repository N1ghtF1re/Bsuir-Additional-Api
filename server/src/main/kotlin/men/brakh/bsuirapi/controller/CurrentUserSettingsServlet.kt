package men.brakh.bsuirapi.controller

import men.brakh.bsuirapi.controller.basic.HttpServletWithErrorHandling
import men.brakh.bsuirapi.controller.basic.JsonServlet
import men.brakh.bsuirapi.extentions.extractBody
import men.brakh.bsuirapi.extentions.writeJson
import men.brakh.bsuirapi.model.authorized
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.UserSettings
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CurrentUserSettingsServlet: HttpServletWithErrorHandling(), JsonServlet {
    val bsuirApi = BsuirApi

    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        authorized(req) { user ->
            resp.writeJson(bsuirApi.getPersonalCV(user).toUserSettings())
        }
    }

    override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        authorized(req) { user ->
            val settings = req.extractBody(UserSettings::class.java)

            bsuirApi.savePublished(user, settings.isPublicProfile)
            bsuirApi.saveSearchJob(user, settings.isSearchJob)
            bsuirApi.saveShowRating(user, settings.isShowRating)
        }
    }
}