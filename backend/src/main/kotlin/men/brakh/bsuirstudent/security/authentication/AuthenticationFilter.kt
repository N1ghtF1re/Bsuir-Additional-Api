package men.brakh.bsuirstudent.security.authentication;

import io.jsonwebtoken.ExpiredJwtException
import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import men.brakh.bsuirstudent.security.authentication.jwt.JwtService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationFilter(
  private val jwtService: JwtService,
  private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {

  companion object {
    const val HEADER_NAME = "Authorization"
  }

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    chain: FilterChain
  ) {
    val requestTokenHeader = request.getHeader(HEADER_NAME)
    var username: String? = null
    var jwtToken: String? = null
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7)
      username = try {
        jwtService.getUsernameFromToken(jwtToken)
      } catch (e: IllegalArgumentException) {
        throw UnauthorizedException("Unable to get JWT Token", e)
      } catch (e: ExpiredJwtException) {
        throw UnauthorizedException("JWT Token has expired", e)
      }
    } else {
      logger.warn("JWT token is not found")
    }
    if (username != null) {
      val userDetails = userDetailsService.loadUserByUsername(username)
      if (jwtService.validateToken(jwtToken, userDetails)) {
        val usernamePasswordAuthenticationToken =
          UsernamePasswordAuthenticationToken(
            userDetails, null,
            userDetails.authorities
          )
        usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
      }
    }
    try {
      chain.doFilter(request, response)
    } finally {
      SecurityContextHolder.getContext().authentication = null
    }
  }


}
