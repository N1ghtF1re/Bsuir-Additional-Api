package men.brakh.bsuirapi.freeauds

import men.brakh.bsuirapi.freeauds.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.freeauds.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import men.brakh.bsuirapi.freeauds.repository.impl.MysqlLessonRepository

object Config {
    val lessonsRepository: LessonRepository = MysqlLessonRepository()
    val connectionFactory: ConnectionFactory
        get() {
            return MysqlConnectionFactory
        }
}