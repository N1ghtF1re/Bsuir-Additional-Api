package men.brakh.bsuirstudent.security.authentication

import men.brakh.bsuirstudent.application.logging.LoggingContext
import men.brakh.bsuirstudent.security.authentication.serviceusers.ServiceUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ServiceUsersAuthenticationFilter(
    private val serviceUserDetailsService: ServiceUserDetailsService,
    private val loggingContext: LoggingContext
) : OncePerRequestFilter() {
    val HEADER_NAME = "X-Service-User-Authorization";

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {

        val token: String? = request.getHeader(HEADER_NAME)

        if (token != null) {
            val userDetails = serviceUserDetailsService.loadUserByUsername(token)

            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails.authorities
                )

            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            loggingContext.authenticatedUser = userDetails.username
        }
        try {
            chain.doFilter(request, response)
        } finally {
            SecurityContextHolder.getContext().authentication = null
        }
    }


}