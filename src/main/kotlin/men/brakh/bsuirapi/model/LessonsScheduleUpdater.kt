package men.brakh.bsuirapi.model

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapi.repository.impl.MysqlLessonRepository
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit



/**
 * Class which every day (at 5:00 a.m.) execute updating of lessons schedule
 */
object LessonsScheduleUpdater {
    private val logger = LoggerFactory.getLogger(LessonsScheduleUpdater::class.java)
    private val conFactory: ConnectionFactory by lazy { Config.connectionFactory }

    private val connection: Connection
        get() {
            return conFactory.getConnection()
        }

    private fun updateDb() {
        logger.info("Scheduled lessons schedule update started")

        connection.use {
            val stmt = connection.createStatement()
            stmt.use {
                stmt.execute("CREATE TABLE _tmp_lessons_ LIKE lessons")
            }
        }

        val lessonsRepo = MysqlLessonRepository("_tmp_lessons_")
        val groups = BsuirApi.getGroups()
        groups.forEach{
            val lessons = BsuirApi.getSchedule(it.name)
            lessonsRepo.add(lessons)
        }

        connection.use {
            val stmt = connection.createStatement()
            stmt.use {
                synchronized(Config.lessonsRepository) {
                    stmt.execute("DROP TABLE lessons")
                    stmt.execute("RENAME TABLE _tmp_lessons_ TO lessons")
                }
            }
        }

        logger.info("Scheduled lessons schedule ended")
    }

    fun start() {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        var nextRun = now.withHour(5).withMinute(0).withSecond(0)
        if (now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1)

        val duration = Duration.between(now, nextRun)
        val initalDelay = duration.seconds

        logger.info("Scheduled lessons will started after ${initalDelay/60/60} hours")

        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate(
                { updateDb() },
                initalDelay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS)
    }
}