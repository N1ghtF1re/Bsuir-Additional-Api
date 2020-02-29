package men.brakh.bsuirstudent.domain.iis.recordbook

import men.brakh.bsuirstudent.application.scheduling.AuthorizedCacheUpdatingScheduler
import men.brakh.bsuirstudent.domain.iis.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.recordbook.mapping.RecordBookBsuirMapping
import men.brakh.bsuirstudent.domain.iis.recordbook.repository.RecordBookRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentialsRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Component
open class RecordBookCacheUpdatingScheduler(
    private val repository: RecordBookRepository,
    private val bsuirService : BsuirStudentService,
    private val recordBookBsuirMapping: RecordBookBsuirMapping,
    credentialsRepository: UserCredentialsRepository,
    userDetailsService: UserDetailsServiceImpl
) : AuthorizedCacheUpdatingScheduler(credentialsRepository, userDetailsService) {
    private val logger = LoggerFactory.getLogger(RecordBookCacheUpdatingScheduler::class.java)

    @Scheduled(cron = "0 0 2 * * *")
    override fun updateCache() {
        super.updateCache()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateEntity(username: String) {
        val recordBook = repository.findOneByNumber(username) ?: return

        val newRecordBook = recordBookBsuirMapping.mapToRecordBook(
            bsuirService.getMarkBook(),
            bsuirService.getDiploma(),
            recordBook.id
        )

        repository.save(newRecordBook)

        logger.info("Record book updated $username")
    }


}