package men.brakh.bsuirstudent.domain.iis.lesson

import men.brakh.bsuirstudent.application.scheduling.CacheUpdatingScheduler
import men.brakh.bsuirstudent.application.bsuir.BsuirScheduleService
import men.brakh.bsuirstudent.domain.iis.lesson.mapping.LessonBsuirMapping
import men.brakh.bsuirstudent.domain.iis.lesson.repository.LessonRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Component
open class LessonCacheUpdatingScheduler(
    private val repository: LessonRepository,
    private val bsuirService: BsuirScheduleService,
    private val lessonBsuirMapping: LessonBsuirMapping
): CacheUpdatingScheduler {
    private val logger = LoggerFactory.getLogger(LessonCacheUpdatingScheduler::class.java)

    @PostConstruct
    fun init() {
        if (repository.count() == 0L) {
            Thread{
                updateCache()
            }.start()
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 12 1 * *")
    override fun updateCache() {
        val groups = bsuirService.getGroups()
        val list = mutableListOf<Lesson>()
        groups.forEach{
            logger.info("Adding schedule for group ${it.name}")
            val lessons = bsuirService.getSchedule(it.name).flatMap { schedule->
                lessonBsuirMapping.mapToLessons(schedule)
            }

            list.addAll(lessons)
        }


        repository.deleteAll()
        repository.saveAll(list)

        logger.info("Lessons were updated")
    }
}
