package men.brakh.bsuirapi.model.jwt.factories

import men.brakh.bsuirapi.model.jwt.JwtToken
import men.brakh.bsuirapi.model.jwt.RawJwtToken
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.User

abstract class JwtTokensAbstractFactory(val key: String, val userRepository: UserRepository) {
    abstract fun fromRaw(rawJwtToken: RawJwtToken): JwtToken
    abstract fun createToken(user: User, minutesToExpires: Int): String
}

