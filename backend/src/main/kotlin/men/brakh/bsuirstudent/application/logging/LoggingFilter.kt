package men.brakh.bsuirstudent.application.logging

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


const val SESSION_ID_HEADER = "SESSION-ID"

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggingFilter(
    private val loggingContext: LoggingContext
) : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        loggingContext.generateSessionID()

        if (request is HttpServletRequest && response is HttpServletResponse) {
            loggingContext.uri = request.requestURI
            response.setHeader(SESSION_ID_HEADER, loggingContext.sessionId)
        }

        try {
            chain.doFilter(request, response)
        } finally {
            loggingContext.clear()
        }
    }
}