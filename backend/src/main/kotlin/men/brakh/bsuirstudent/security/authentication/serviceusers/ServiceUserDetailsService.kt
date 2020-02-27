package men.brakh.bsuirstudent.security.authentication.serviceusers

import men.brakh.bsuirstudent.security.Role
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ServiceUserDetailsService(
    @Value("\${security.news.creator.token}") private val newsCreatorToken: String
) : UserDetailsService {

    @Transactional(readOnly = true)
    @Throws(
        UsernameNotFoundException::class
    )
    override fun loadUserByUsername(token: String): UserDetails {
        return when(token) {
            newsCreatorToken -> User.builder()
                .password(token)
                .username("NewsCreator")
                .roles(Role.NEWS_CREATOR)
                .build()

            else -> throw UsernameNotFoundException("Service user with token is not found $token")
        }


    }

}