package men.brakh.bsuirstudent.domain.iis.student

import men.brakh.bsuirstudent.application.scheduling.AuthorizedCacheUpdatingScheduler
import men.brakh.bsuirstudent.domain.iis.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.student.mapping.StudentBsuirMapping
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserCredentialsRepository
import men.brakh.bsuirstudent.security.authentication.credentials.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
open class StudentCacheUpdatingScheduler(
    private val repository: StudentRepository,
    private val bsuirService : BsuirStudentService,
    private val studentBsuirMapping: StudentBsuirMapping,
    credentialsRepository: UserCredentialsRepository,
    userDetailsService: UserDetailsServiceImpl
) : AuthorizedCacheUpdatingScheduler(credentialsRepository, userDetailsService) {
    private val logger = LoggerFactory.getLogger(StudentCacheUpdatingScheduler::class.java)

    @Scheduled(cron = "0 0 4 ? * * *")
    override fun updateCache() {
        super.updateCache()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun updateEntity(username: String) {
        val student = repository.findOneByRecordBookNumber(username) ?: return

        val newsStudent = studentBsuirMapping.mapPersonalCvToStudent( bsuirService.getPersonalCV(), student.id)

        repository.save(newsStudent)

        logger.info("Updated student $username")
    }


}