package men.brakh.bsuirapi.controller.basic

import com.google.gson.JsonParseException
import io.jsonwebtoken.ExpiredJwtException
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.extentions.writeError
import org.slf4j.LoggerFactory
import java.text.ParseException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class HttpServletWithErrorHandling: HttpServlet() {
    val logger = LoggerFactory.getLogger("Exception Handling")
    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            super.service(req, resp)
        } catch (e: Exception) {
            handleError(e, resp)
        }
    }


    private fun handleError(e: Exception, resp: HttpServletResponse) {
        when(e.javaClass) {
            ParseException::class.java -> resp.writeError("Invalid date/time format")
            JsonParseException::class.java -> resp.writeError("Invalid json")
            UnauthorizedException::class.java -> resp.writeError("Unauthorized", 401)
            ExpiredJwtException::class.java -> resp.writeError("Expired jwt token", 401)
            else -> {
                resp.writeError(e.message ?: e.toString(), 400)
                logger.error("", e)
            }
        }
    }
}