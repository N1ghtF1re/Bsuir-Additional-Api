package men.brakh.bsuirstudent.security

import men.brakh.bsuirstudent.security.authentication.JwtAuthenticationFilter
import men.brakh.bsuirstudent.security.authentication.ServiceUsersAuthenticationFilter
import men.brakh.bsuirstudent.security.authentication.bsuir.BsuirAuthenticationProvider
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class WebSecurityConfig(
  jwtUserDetailsService: UserDetailsServiceImpl,
  jwtAuthenticationFilter: JwtAuthenticationFilter,
  serviceUsersAuthenticationFilter: ServiceUsersAuthenticationFilter,
  authenticationEntryPoint: AuthenticationEntryPoint,
  authenticationProvider: BsuirAuthenticationProvider
) :
  WebSecurityConfigurerAdapter() {
  private val jwtUserDetailsService: UserDetailsService
  private val jwtAuthenticationFilter: JwtAuthenticationFilter
  private val serviceUsersAuthenticationFilter: ServiceUsersAuthenticationFilter
  private val authenticationEntryPoint: AuthenticationEntryPoint
  private val authenticationProvider: BsuirAuthenticationProvider
  @Throws(Exception::class)
  public override fun configure(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(authenticationProvider)
  }

  @Autowired
  @Throws(Exception::class)
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(jwtUserDetailsService)
      .passwordEncoder(passwordEncoder())
  }

  @Bean
  open fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  @Throws(Exception::class)
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  @Throws(Exception::class)
  override fun configure(httpSecurity: HttpSecurity) {
    httpSecurity
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .permitAll() //allow CORS option calls
      .antMatchers("/api/v2/auth", "/api/v2/news/**", "/api/v2/auditoriums/**", "/api/v2/buildings", "/heath")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
    httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    httpSecurity.addFilterBefore(
      serviceUsersAuthenticationFilter,
      UsernamePasswordAuthenticationFilter::class.java
    )
  }

  @Bean
  open fun corsConfigurer(
    @Value("\${security.cors.origins}") corsOrigins: String?
  ): WebMvcConfigurer {
    return object : WebMvcConfigurer {
      override fun addCorsMappings(registry: CorsRegistry) {
        registry
          .addMapping("/**")
          .allowedOrigins(corsOrigins)
          .allowedHeaders("*")
          .allowedMethods("*")
          .allowCredentials(true)
      }
    }
  }

  init {
    this.jwtUserDetailsService = jwtUserDetailsService
    this.jwtAuthenticationFilter = jwtAuthenticationFilter
    this.serviceUsersAuthenticationFilter = serviceUsersAuthenticationFilter
    this.authenticationEntryPoint = authenticationEntryPoint
    this.authenticationProvider = authenticationProvider
  }
}