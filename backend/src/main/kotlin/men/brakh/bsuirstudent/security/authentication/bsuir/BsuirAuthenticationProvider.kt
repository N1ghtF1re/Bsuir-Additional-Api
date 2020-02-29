package men.brakh.bsuirstudent.security.authentication.bsuir

import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import men.brakh.bsuirstudent.application.bsuir.BsuirApiExecutor
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentials
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentialsRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
open class BsuirAuthenticationProvider(
    private val bsuirApi: BsuirApiExecutor,
    private val passwordEncrypt: PasswordEncrypt,
    private val userCredentialsRepository: UserCredentialsRepository
) : AuthenticationProvider {

    private fun isCorrectCredentials(username: String, password: String)
        = bsuirApi.tryAuth(username, password) != null

    private fun updateCredentials(username: String, password: String) {
        val credentialsInDb = userCredentialsRepository.findOneByUsername(username);

        if (credentialsInDb == null) {
            userCredentialsRepository.save(UserCredentials(
                username = username,
                password = passwordEncrypt.encrypt(password)
            ))
        } else {
            userCredentialsRepository.save(credentialsInDb.copy(
                username = username,
                password = passwordEncrypt.encrypt(password)
            ))
        }
    }

    override fun authenticate(auth: Authentication): Authentication {
        val username: String = auth.name
        val password: String = auth.credentials.toString()


        return if (isCorrectCredentials(username, password)) {
            updateCredentials(username, password)
            UsernamePasswordAuthenticationToken(username, password, listOf())
        } else {
            throw UnauthorizedException("Invalid login or password")
        }
    }

    override fun supports(auth: Class<*>): Boolean {
        return auth == UsernamePasswordAuthenticationToken::class.java
    }

}