package men.brakh.bsuirapi.model.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import men.brakh.bsuirapicore.model.data.User
import java.util.*

abstract class JwtToken(private val rawToken: RawJwtToken) {
    protected fun parseClaims(key: String): Jws<Claims> = Jwts.parser()
            .setSigningKey(Base64.getDecoder().decode(key))
            .parseClaimsJws(rawToken.token)

    abstract fun getUser(): User?
}