package men.brakh.bsuirstudent.application.scheduling

import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentials
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentialsRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


abstract class AuthorizedCacheUpdatingScheduler(
    private val credentialsRepository: UserCredentialsRepository,
    private val userDetailsService: UserDetailsServiceImpl
) : CacheUpdatingScheduler {
    private val logger = LoggerFactory.getLogger(AuthorizedCacheUpdatingScheduler::class.java)

    abstract fun updateEntity(username: String)

    override fun updateCache() {
        logger.info("Cache updating started: ${this.javaClass.simpleName}")
        val updatingQueue: Queue<UserCredentials> = LinkedList<UserCredentials>(credentialsRepository.findAll())

        while(updatingQueue.size != 0) {
            val credentials: UserCredentials = updatingQueue.remove()

            val userDetails = userDetailsService.loadUserByUsername(credentials.username)
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null,
                    userDetails.authorities
                )

            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken

            try {
                updateEntity(credentials.username)

                Thread.sleep(2000)
            } catch (e: Exception) {
                logger.error("Updating error", e)
            } finally {
                SecurityContextHolder.getContext().authentication = null
            }


        }
    }
}