package men.brakh.bsuirstudent.security.authentication.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtService(private val settings: JwtSettings) {
  fun getUsernameFromToken(token: String?): String {
    return getClaimFromToken(
      token,
      Function { obj: Claims -> obj.subject }
    )
  }

  fun getExpirationDateFromToken(token: String?): Date {
    return getClaimFromToken(token,
      Function { obj: Claims -> obj.expiration }
    )
  }

  fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
    val claims = getAllClaimsFromToken(token)
    return claimsResolver.apply(claims)
  }

  private fun getAllClaimsFromToken(token: String?): Claims {
    return Jwts.parser()
      .setSigningKey(settings.tokenSigningKey)
      .parseClaimsJws(token)
      .body
  }

  fun isTokenExpired(token: String?): Boolean {
    val expiration = getExpirationDateFromToken(token)
    return expiration.before(Date())
  }

  fun generateToken(userDetails: UserDetails): String {
    val claims: Map<String, Any> = HashMap()
    return doGenerateToken(claims, userDetails.username)
  }

  private fun nexAugust(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar.set(Calendar.HOUR_OF_DAY, 12)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.DAY_OF_MONTH, 31)
    calendar.set(Calendar.MONTH, 7)
    if (Calendar.getInstance().get(Calendar.MONTH) in 8..11) {
      calendar.add(Calendar.YEAR, 1)
    }
    return calendar.time
  }

  private fun doGenerateToken(
    claims: Map<String, Any>,
    subject: String
  ): String {
    return Jwts.builder().setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(Date())
      .setExpiration(nexAugust())
      .signWith(SignatureAlgorithm.HS512, settings.tokenSigningKey)
      .compact()
  }

  fun validateToken(token: String?, userDetails: UserDetails): Boolean {
    val username = getUsernameFromToken(token)
    return username == userDetails.username && !isTokenExpired(token)
  }

}