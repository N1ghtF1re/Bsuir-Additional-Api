package men.brakh.bsuirapi.servlets.api.v1.rating

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import men.brakh.bsuirapi.servlets.basic.Parameters
import javax.servlet.annotation.WebServlet


@WebServlet("/api/v1/rating", loadOnStartup = 1)
class RatingServlet : BasicHttpServlet() {
    private val ratingService =  Config.ratingService

    override fun handle() {
        get { parameters: Parameters ->
            ratingService.getRating(parameters.getRequired("year").toInt(),
                    parameters.getRequired("specialityId").toInt())
        }
    }

}