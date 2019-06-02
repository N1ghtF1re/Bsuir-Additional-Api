package men.brakh.bsuirapi

import men.brakh.bsuirapi.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.model.LessonsScheduleUpdater
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapi.repository.*
import men.brakh.bsuirapi.repository.impl.*
import men.brakh.bsuirapicore.model.data.Lesson
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object Config {
    private val logger = LoggerFactory.getLogger(Config::class.java)

    val lessonsRepository: LessonRepository = MysqlLessonRepository()
    val auditoriumRepository: AuditoriumRepository = MysqlAuditoriumRepository()
    val newsSourceRepository: NewsSourceRepository = MysqlNewsSourceRepository()
    val newsRepository: NewsRepository = MysqlNewsRepository()
    val tokenRepository: TokenRepository = MysqlTokenRepository()
    val connectionFactory: ConnectionFactory
        get() {
            return MysqlConnectionFactory
        }

    val newsAtPage: Int

    val bsuirApiHost: String


    init {
        val propsPath: String = this.javaClass.classLoader.getResource("config.properties")?.path
                ?: throw FileNotFoundException("Config not found")

        val configProps = Properties()
        configProps.load(FileInputStream(propsPath))

        bsuirApiHost = configProps.getProperty("bsuir.api.host")
        newsAtPage = configProps.getProperty("default.news.at.page").toInt()


        if(auditoriumRepository.count() == 0) {
            logger.info("Loading the auditoriums list ... It takes a few minutes...")
            val auds: List<Auditorium> = BsuirApi.getAuditoriums()
            auditoriumRepository.add(auds)
            logger.info("Auditoriums list loaded!")
        }

        if(lessonsRepository.count() == 0) {
            logger.info("Loading the lessons list ... It takes a tens of minutes...")
            val lessons: List<Lesson> = BsuirApi.getGroups().flatMap { BsuirApi.getSchedule(it.name) }
            lessonsRepository.add(lessons)
            logger.info("Lessons list loaded!")
        }

        LessonsScheduleUpdater.start()
    }
}