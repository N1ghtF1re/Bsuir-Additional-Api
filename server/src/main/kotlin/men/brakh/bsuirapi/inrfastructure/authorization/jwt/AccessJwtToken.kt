package men.brakh.bsuirapi.inrfastructure.authorization.jwt

import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest

class AccessJwtToken(rawToken: RawJwtToken, private val key: String, private val userRepository: UserRepository): JwtToken(rawToken) {
    override fun getUser(): UserAuthorizationRequest? {
        val username = parseClaims(key).body.subject
        return userRepository.find(login = username)
    }
}