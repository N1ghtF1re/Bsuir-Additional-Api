package men.brakh.bsuirapi.freeauds

import men.brakh.bsuirapi.freeauds.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.freeauds.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.Lesson
import men.brakh.bsuirapi.freeauds.model.bsuirapi.BsuirApi
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import men.brakh.bsuirapi.freeauds.repository.impl.MysqlAuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.impl.MysqlLessonRepository
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object Config {
    private val logger = LoggerFactory.getLogger(Config::class.java)

    val lessonsRepository: LessonRepository = MysqlLessonRepository()
    val auditoriumRepository: AuditoriumRepository = MysqlAuditoriumRepository()
    val connectionFactory: ConnectionFactory
        get() {
            return MysqlConnectionFactory
        }

    val bsuirApiHost: String

    init {
        val propsPath: String = this.javaClass.classLoader.getResource("config.properties")?.path ?: throw FileNotFoundException("Config not found")

        val configProps = Properties()
        configProps.load(FileInputStream(propsPath))

        bsuirApiHost = configProps.getProperty("bsuir.api.host")

        if(auditoriumRepository.findAll().count() == 0) {
            logger.info("Loading the auditoriums list ... It takes a few minutes...")
            val auds: List<Auditorium> = BsuirApi.getAuditoriums()
            auditoriumRepository.add(auds)
            logger.info("Auditoriums list loaded!")
        }

        if(lessonsRepository.findAll().count() == 0) {
            logger.info("Loading the lessons list ... It takes a tens of minutes...")
            val lessons: List<Lesson> = BsuirApi.getGroups().flatMap { BsuirApi.getSchedule(it.name) }
            lessonsRepository.add(lessons)
            logger.info("Lessons list loaded!")
        }
    }
}