package men.brakh.bsuirapi.inrfastructure.bsuirapiinitializer

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.inrfastructure.schedulers.LessonsScheduleUpdater
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapi.repository.LessonRepository
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.Lesson
import org.slf4j.LoggerFactory

object BsuirApiInitializer {
    private val logger = LoggerFactory.getLogger(BsuirApiInitializer::class.java)

    fun initLessonsAndAuditoriums(auditoriumRepository: AuditoriumRepository,
                                  lessonRepository: LessonRepository) {
        if(auditoriumRepository.count() == 0) {
            logger.info("Loading the auditoriums list ... It takes a few minutes...")
            val auds: List<Auditorium> = Config.bsuirApi.getAuditoriums()
            auditoriumRepository.add(auds)
            logger.info("Auditoriums list loaded!")
        }

        if(lessonRepository.count() == 0) {
            logger.info("Loading the lessons list ... It takes a tens of minutes...")
            val lessons: List<Lesson> = Config.bsuirApi.getGroups().flatMap { Config.bsuirApi.getSchedule(it.name) }
            lessonRepository.add(lessons)
            logger.info("Lessons list loaded!")
        }
        LessonsScheduleUpdater.start()
    }
}