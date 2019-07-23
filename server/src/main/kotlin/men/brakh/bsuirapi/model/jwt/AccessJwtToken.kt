package men.brakh.bsuirapi.model.jwt

import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.User

class AccessJwtToken(rawToken: RawJwtToken, private val key: String, private val userRepository: UserRepository): JwtToken(rawToken) {
    override fun getUser(): User? {
        val username = parseClaims(key).body.subject
        return userRepository.find(login = username)
    }
}