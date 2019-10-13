package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.Lesson
import men.brakh.bsuirapicore.model.data.Weeks
import men.brakh.bsuirapi.repository.AuditoriumRepository
import men.brakh.bsuirapi.repository.LessonRepository
import org.slf4j.LoggerFactory
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Time


class MysqlLessonRepository(tableName: String, val audRepo: AuditoriumRepository)
                : MysqlRepository<Lesson>(tableName), LessonRepository {

    constructor(audRepo: AuditoriumRepository) : this("lessons", audRepo)

    private val logger = LoggerFactory.getLogger(MysqlLessonRepository::class.java)



    override fun extractor(resultSet: ResultSet): Lesson? {
        val aud: Auditorium = audRepo.findById(resultSet.getLong("auditorium")) ?: return null
        return Lesson(
                id = resultSet.getLong("id"),
                aud = aud,
                day = resultSet.getInt("day"),
                weeks = Weeks(resultSet.getInt("weeks")),
                startTime = resultSet.getTime("start_time"),
                endTime = resultSet.getTime("end_time"),
                group = resultSet.getString("group")
        )
    }

    override fun add(entity: Lesson): Lesson {
        connection.use {

            val statement = it.prepareStatement("INSERT INTO `$tableName` " +
                    "(`auditorium`, `weeks`, `day`, `start_time`, `end_time`, `group`) VALUES(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            val aud = if(entity.aud.id == -1L) {
                audRepo.find(
                        building = entity.aud.building,
                        floor = entity.aud.floor,
                        name = entity.aud.name
                ).firstOrNull() ?: {logger.warn("Added new auditorium: ${entity.aud}"); audRepo.add(entity.aud)}()
            } else {
                entity.aud
            }
            statement.use {stmt: PreparedStatement ->
                stmt.setLong(1, aud.id)
                stmt.setInt(2, entity.weeks.mask)
                stmt.setInt(3, entity.day)
                stmt.setTime(4, entity.startTime)
                stmt.setTime(5, entity.endTime)
                stmt.setString(6, entity.group)


                stmt.executeUpdate()

                return entity.copy(id = stmt.generatedId)
            }
        }
    }

    override fun find(weeks: Weeks?, time: Time?, aud: Auditorium?, day: Int?, building: Int?, floor: Int?): List<Lesson> {
        val initQuery = "SELECT l.id, l.auditorium, l.weeks, l.day, l.start_time, l.end_time, l.group FROM $tableName as l"

        val condition: String = if(floor == null && building == null)
            "WHERE"
        else
            "INNER JOIN auditoriums as a ON l.auditorium = a.id AND "

        val conditions
                = mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()


        if(weeks != null) conditions.add(
                (if(weeks.isSingleDay()) "l.weeks & ? != 0" else "l.weeks = ?")
                        to { stmt, index -> stmt.setInt(index, weeks.mask) }
        )

        if(day != null) conditions.add(
                "l.day = ?" to {stmt, index -> stmt.setInt(index, day)}
        )

        if(time != null) {
            conditions.add(
                    "l.start_time <= ?" to { stmt, index -> stmt.setTime(index, time)}
            )
            conditions.add(
                    "l.end_time >= ?" to {stmt, index -> stmt.setTime(index, time)}
            )
        }

        if(aud != null) conditions.add(
                "l.auditorium = ?" to {stmt, index -> stmt.setLong(index, aud.id)}
        )

        if(building != null) conditions.add(
                "a.building = ?" to {stmt, index -> stmt.setInt(index, building)}
        )

        if(floor != null) conditions.add(
                "a.floor = ?" to {stmt, index -> stmt.setInt(index, floor)}
        )


        val finalQuery = "$initQuery $condition ${conditions.map{it.first}.joinToString(separator = " AND ")}"

        connection.use {
            val statement = it.prepareStatement(finalQuery)
            statement.use { stmt ->
                for((index, cond) in conditions.withIndex()) {
                    cond.second(stmt, index + 1) // Установка параметров запроса
                }

                return extract(stmt)
            }
        }
    }


    override fun update(entity: Lesson): Lesson {
        connection.use {
            val statement = it.prepareStatement("UPDATE `$tableName` SET `auditorium` = ?, " +
                    "`weeks` = ?, `start_time` = ?, `end_time` = ?, `group` = ? WHERE `id` = ?")
            statement.use {stmt ->
                stmt.setLong(1, entity.aud.id)
                stmt.setInt(2, entity.weeks.mask)
                stmt.setTime(3, entity.startTime)
                stmt.setTime(4, entity.endTime)
                stmt.setString(5, entity.group)

                stmt.setLong(6, entity.id)

                stmt.execute()

                return entity
            }
        }


    }

    override fun count(): Int {
        connection.use {
            val statement = connection.createStatement()
            statement.use { stmt ->
                val resultSet: ResultSet = stmt.executeQuery("SELECT COUNT(*) FROM $tableName")
                resultSet.next()
                return resultSet.getInt(1)
            }

        }
    }

}

