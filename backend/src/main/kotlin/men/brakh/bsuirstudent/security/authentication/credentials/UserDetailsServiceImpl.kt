package men.brakh.bsuirstudent.security.authentication.credentials;

import men.brakh.bsuirstudent.security.Role
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
open class UserDetailsServiceImpl(
  private val userCredentialsRepository: UserCredentialsRepository
) : UserDetailsService {

  @Transactional(readOnly = true)
  @Throws(
    UsernameNotFoundException::class
  )
  override fun loadUserByUsername(username: String): UserDetails {
    val credentials = userCredentialsRepository.findOneByUsername(username)
      ?: throw UsernameNotFoundException(username)


    return User.builder()
      .password(credentials.password)
      .username(credentials.username)
      .roles(Role.STUDENT)
      .build()

  }

}
