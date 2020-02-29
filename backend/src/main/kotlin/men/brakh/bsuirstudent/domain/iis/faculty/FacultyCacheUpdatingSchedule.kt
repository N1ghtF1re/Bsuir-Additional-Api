package men.brakh.bsuirstudent.domain.iis.faculty

import men.brakh.bsuirstudent.application.bsuir.BsuirFacultyService
import men.brakh.bsuirstudent.application.scheduling.CacheUpdatingScheduler
import men.brakh.bsuirstudent.domain.iis.faculty.mapping.FacultyBsuirMapping
import men.brakh.bsuirstudent.domain.iis.faculty.repository.FacultyRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class FacultyCacheUpdatingSchedule(
    private val repository: FacultyRepository,
    private val bsuirService: BsuirFacultyService,
    private val bsuirMapping: FacultyBsuirMapping
) : CacheUpdatingScheduler {

    @Transactional
    @Scheduled(cron = "0 0 0 3 9 ?")
    override fun updateCache() {
        repository.deleteAll()
        repository.saveAll(
            bsuirMapping.mapToFaculties(
                bsuirService.getFaculties(),
                bsuirService.getSpecialities()
            )
        )
    }

}