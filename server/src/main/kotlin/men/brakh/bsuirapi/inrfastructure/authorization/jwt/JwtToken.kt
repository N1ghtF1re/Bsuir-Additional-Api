package men.brakh.bsuirapi.inrfastructure.authorization.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import java.util.*

abstract class JwtToken(private val rawToken: RawJwtToken) {
    protected fun parseClaims(key: String): Jws<Claims> {
        try {
            return Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(key))
                    .parseClaimsJws(rawToken.token)
        } catch (e: Exception) {
            throw UnauthorizedException("Invalid JWT Token")
        }
    }

    abstract fun getUser(): UserAuthorizationRequest?
}