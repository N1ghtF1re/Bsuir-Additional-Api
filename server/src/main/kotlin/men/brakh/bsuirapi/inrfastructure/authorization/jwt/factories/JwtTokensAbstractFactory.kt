package men.brakh.bsuirapi.inrfastructure.authorization.jwt.factories

import men.brakh.bsuirapi.inrfastructure.authorization.jwt.JwtToken
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.RawJwtToken
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import java.util.*

abstract class JwtTokensAbstractFactory(val key: String, val userRepository: UserRepository) {
    abstract fun fromRaw(rawJwtToken: RawJwtToken): JwtToken
    abstract fun createToken(userAuthorizationRequest: UserAuthorizationRequest, minutesToExpires: Int): String
    abstract fun createToken(userAuthorizationRequest: UserAuthorizationRequest, expirationDate: Date): String
}

