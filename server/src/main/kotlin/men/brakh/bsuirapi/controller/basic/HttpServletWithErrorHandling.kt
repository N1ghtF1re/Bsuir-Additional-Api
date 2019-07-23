package men.brakh.bsuirapi.controller.basic

import com.google.gson.JsonParseException
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.extentions.writeError
import java.text.ParseException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class HttpServletWithErrorHandling: HttpServlet() {
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
            else -> resp.writeError(e.message ?: e.toString(), 400)
        }
    }
}