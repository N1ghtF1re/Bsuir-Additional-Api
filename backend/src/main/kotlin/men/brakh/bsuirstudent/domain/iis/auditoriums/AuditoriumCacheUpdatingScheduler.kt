package men.brakh.bsuirstudent.domain.iis.auditoriums

import men.brakh.bsuirstudent.application.bsuir.BsuirScheduleService
import men.brakh.bsuirstudent.application.scheduling.CacheUpdatingScheduler
import men.brakh.bsuirstudent.domain.iis.auditoriums.mapping.AuditoriumBsuirMapping
import men.brakh.bsuirstudent.domain.iis.auditoriums.repository.AuditoriumRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Component
open class AuditoriumCacheUpdatingScheduler(
    private val bsuirService: BsuirScheduleService,
    private val bsuirMapping: AuditoriumBsuirMapping,
    private val repository: AuditoriumRepository
) : CacheUpdatingScheduler {
    private val logger = LoggerFactory.getLogger(AuditoriumCacheUpdatingScheduler::class.java)

    @PostConstruct
    fun init() {
        if (repository.count() == 0L) {
            updateCache()
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 12 1 * *")
    override fun updateCache() {
        synchronized(repository) {
            val auditoriums = bsuirService.getAuditoriums().mapNotNull { bsuirMapping.mapToAuditorium(it) }
            repository.deleteAll()
            repository.saveAll(auditoriums)

            logger.info("Auditoriums were updated")
        }
    }
}
