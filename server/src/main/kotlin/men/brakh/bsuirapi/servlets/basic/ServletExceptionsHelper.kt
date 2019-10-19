package men.brakh.bsuirapi.servlets.basic

import com.google.gson.JsonParseException
import io.jsonwebtoken.ExpiredJwtException
import men.brakh.bsuirapi.EntityAlreadyExistsException
import men.brakh.bsuirapi.NotFoundException
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.text.ParseException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object ServletExceptionsHelper {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun handleError(e: Exception, req: HttpServletRequest, resp: HttpServletResponse) {
        logger.warn("Exception: ${e.message}. ${req.method} ${req.requestURL}")
        when(e) {
            is ParseException -> resp.writeError("Invalid date/time format")
            is NumberFormatException -> resp.writeError("Parameter must be int")
            is JsonParseException -> resp.writeError("Invalid json")
            is UnauthorizedException -> resp.writeError(e.message ?: "Unauthorized",
                    HttpServletResponse.SC_UNAUTHORIZED)
            is ExpiredJwtException -> resp.writeError("Expired jwt token",
                    HttpServletResponse.SC_UNAUTHORIZED)
            is EntityAlreadyExistsException -> resp.writeError(e.message ?: "Entity already exists",
                    HttpServletResponse.SC_CONFLICT)
            is UserNotFoundException -> resp.writeError(e.message ?: "User is not found")
            is NotFoundException -> resp.writeError(e.message ?: "Not found",
                    HttpServletResponse.SC_NOT_FOUND)
            is IllegalArgumentException -> resp.writeError(e.message ?: "Invalid argument(s)")
            is AccessDeniedException -> resp.writeError(e.message ?: "Access Denied",
                    HttpServletResponse.SC_FORBIDDEN)
            is IllegalStateException  -> {
                if ("FileSizeLimitExceededException" in e.message ?: "") {
                    val message = e.message!!.split(":").getOrNull(1) ?: "File is too big"
                    resp.writeError(message, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE)
                } else {
                    resp.writeError(e.message ?: "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                }
            }

            else -> {
                resp.writeError(e.message ?: e.toString(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                logger.error("${req.method}: ${req.requestURL} error")
                logger.error("", e)
            }
        }
    }
}