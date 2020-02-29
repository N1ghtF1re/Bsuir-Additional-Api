package men.brakh.bsuirstudent.domain.iis.group

import men.brakh.bsuirstudent.application.scheduling.AuthorizedCacheUpdatingScheduler
import men.brakh.bsuirstudent.domain.iis.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.group.mapping.GroupBsuirMapping
import men.brakh.bsuirstudent.domain.iis.group.repository.GroupRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentialsRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Component
open class GroupCacheUpdatingScheduler(
    private val repository: GroupRepository,
    private val bsuirService : BsuirStudentService,
    private val groupBsuirMapping: GroupBsuirMapping,
    credentialsRepository: UserCredentialsRepository,
    userDetailsService: UserDetailsServiceImpl
) : AuthorizedCacheUpdatingScheduler(credentialsRepository, userDetailsService) {
    private val logger = LoggerFactory.getLogger(GroupCacheUpdatingScheduler::class.java)

    @Scheduled(cron = "0 0 3 * * SAT")
    override fun updateCache() {
        super.updateCache()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateEntity(username: String) {
        val group = repository.findOneByNumber(username) ?: return

        val newGroup = groupBsuirMapping.mapToGroup( bsuirService.getGroup(), group.id)

        repository.save(newGroup)

        logger.info("Updated group ${group.number}")
    }


}