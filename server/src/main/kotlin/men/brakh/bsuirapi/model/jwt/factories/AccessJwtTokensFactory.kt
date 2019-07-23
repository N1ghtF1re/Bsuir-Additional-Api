package men.brakh.bsuirapi.model.jwt.factories

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import men.brakh.bsuirapi.model.jwt.AccessJwtToken
import men.brakh.bsuirapi.model.jwt.JwtToken
import men.brakh.bsuirapi.model.jwt.RawJwtToken
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.User
import java.util.*


class AccessJwtTokensFactory(key: String, userRepository: UserRepository) : JwtTokensAbstractFactory(key, userRepository) {
    override fun createToken(user: User, minutesToExpires: Int): String{
        val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key))

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MINUTE, minutesToExpires)

        return Jwts
                .builder()
                .setSubject(user.login)
                .signWith(secretKey)
                .setExpiration(calendar.time)
                .compact()

    }

    override fun fromRaw(rawJwtToken: RawJwtToken): JwtToken
            = AccessJwtToken(key = key, userRepository = userRepository, rawToken = rawJwtToken)
}