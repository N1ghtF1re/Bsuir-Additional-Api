package men.brakh.bsuirstudent.security.authentication.jwt;

import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import men.brakh.bsuirstudent.security.authentication.dto.AuthenticationRequest
import men.brakh.bsuirstudent.security.authentication.dto.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin
class JwtAuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsServiceImpl
) {

    @PostMapping("/api/v2/auth")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        tryAuthenticate(authenticationRequest.username, authenticationRequest.password)
        val userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.username)
        val token = jwtService.generateToken(userDetails)
        return AuthenticationResponse(token)
    }

    private fun tryAuthenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw UnauthorizedException("USER_DISABLED", e)
        } catch (e: RuntimeException) {
            throw UnauthorizedException("Invalid credentials", e)
        }
    }


}