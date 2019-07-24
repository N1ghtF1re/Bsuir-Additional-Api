package men.brakh.bsuirapi.controller.basic

import com.google.gson.JsonParseException
import io.jsonwebtoken.ExpiredJwtException
import men.brakh.bsuirapi.NotFoundException
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.extentions.writeError
import org.slf4j.LoggerFactory
import java.text.ParseException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class HttpServletWithErrorHandling: HttpServlet() {
    private val logger = LoggerFactory.getLogger("Exception Handling")
    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {

        resp.characterEncoding = "UTF-8"
        resp.setHeader("Access-Control-Allow-Origin", "*")

        if(this is JsonServlet) {
            resp.contentType = "application/json"
        }

        try {
            super.service(req, resp)
        } catch (e: Exception) {
            handleError(e, resp)
        }
    }

    private fun handleError(e: Exception, resp: HttpServletResponse) {
        when(e) {
            is ParseException -> resp.writeError("Invalid date/time format")
            is JsonParseException -> resp.writeError("Invalid json")
            is UnauthorizedException -> resp.writeError("Unauthorized", 401)
            is ExpiredJwtException -> resp.writeError("Expired jwt token", 401)
            is NotFoundException -> resp.writeError(e.message ?: "Not found", 404)
            is IllegalArgumentException -> resp.writeError("Invalid argument(s)")
            else -> {
                resp.writeError(e.message ?: e.toString(), 400)
                logger.error("", e)
            }
        }
    }
}