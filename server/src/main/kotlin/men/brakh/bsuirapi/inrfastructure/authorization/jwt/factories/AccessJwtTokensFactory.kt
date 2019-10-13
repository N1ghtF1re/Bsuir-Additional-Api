package men.brakh.bsuirapi.inrfastructure.authorization.jwt.factories

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.AccessJwtToken
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.JwtToken
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.RawJwtToken
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import java.util.*


class AccessJwtTokensFactory(key: String, userRepository: UserRepository) : JwtTokensAbstractFactory(key, userRepository) {
    override fun createToken(userAuthorizationRequest: UserAuthorizationRequest, expirationDate: Date): String{
        val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key))

        return Jwts
                .builder()
                .setSubject(userAuthorizationRequest.login)
                .signWith(secretKey)
                .setExpiration(expirationDate)
                .compact()
    }

    override fun createToken(userAuthorizationRequest: UserAuthorizationRequest, minutesToExpires: Int): String{
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MINUTE, minutesToExpires)

        return createToken(userAuthorizationRequest, calendar.time)
    }

    override fun fromRaw(rawJwtToken: RawJwtToken): JwtToken
            = AccessJwtToken(key = key, userRepository = userRepository, rawToken = rawJwtToken)
}