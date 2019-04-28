package men.brakh.bsuirapi.freeauds

import men.brakh.bsuirapi.freeauds.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.freeauds.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import men.brakh.bsuirapi.freeauds.repository.impl.MysqlAuditoriumRepository
import men.brakh.bsuirapi.freeauds.repository.impl.MysqlLessonRepository
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object Config {
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
    }
}