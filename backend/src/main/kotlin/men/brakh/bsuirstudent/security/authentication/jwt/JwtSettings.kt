package men.brakh.bsuirstudent.security.authentication.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
open class JwtSettings {
  var expiration: Int = 1
  lateinit var tokenIssuer: String
  lateinit var tokenSigningKey: String
}
