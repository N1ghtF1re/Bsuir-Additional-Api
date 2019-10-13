package men.brakh.bsuirapi.servlets.filter

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.RawJwtToken
import org.slf4j.LoggerFactory
import javax.servlet.*
import javax.servlet.http.HttpServletRequest


class AuthenticationFilter : Filter {
    private val authenticationManager = Config.authenticationManager
    private val accessJwtTokensFactory = Config.accessJwtTokenFactory
    private val tokenRepository = Config.tokenRepository
    private val logger = LoggerFactory.getLogger(AuthenticationFilter::class.java)


    override fun init(config: FilterConfig?) {
    }

    override fun destroy() {
        authenticationManager.removeAuthentication()
    }

    private fun authorizeJwt(token: String) {
        val jwtToken =  accessJwtTokensFactory.fromRaw(RawJwtToken(token))

        val userCredentials = jwtToken.getUser() ?: return

        authenticationManager.authenticate(userCredentials.login)
    }

    private fun authorizeNewsCreatorServiceUser(token: String) {
        val serviceToken = tokenRepository.find(token = token) ?: return

        authenticationManager.authenticate(serviceToken.token, serviceToken.permissions)
    }


    override fun doFilter(request: ServletRequest, resp: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest) {
            try {
                authenticationManager.removeAuthentication()

                val jwtStringToken: String? = request.getHeader("Authorization")
                val newsCreatorToken: String? = request.getHeader("X-News-Authorization")

                if (jwtStringToken != null) {
                    authorizeJwt(jwtStringToken)
                }

                if (newsCreatorToken != null) {
                    authorizeNewsCreatorServiceUser(newsCreatorToken)
                }
            } catch (e: Exception) {
                authenticationManager.removeAuthentication()
                logger.error("Authorization processing error", e)
            }

        }
        chain.doFilter(request, resp)
    }
}