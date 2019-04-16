package men.brakh.bsuirapi.freeauds.repository.impl

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.freeauds.model.*
import men.brakh.bsuirapi.freeauds.repository.LessonRepository
import java.sql.*


class MysqlLessonRepository: LessonRepository {

    private val conFactory: ConnectionFactory = Config.connectionFactory
    private val tableName: String = "lessons"

    private val connection: Connection
        get() {
            return conFactory.getConnection()
        }


    override fun add(entity: Lesson): Lesson {
        connection.use {

            val statement = it.prepareStatement("INSERT INTO `$tableName` " +
                    "(`auditorium`, `weeks`, `start_time`, `end_time`, `group`) VALUES(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use {stmt: PreparedStatement ->
                stmt.setLong(1, entity.aud.id)
                stmt.setInt(2, entity.weeks.value)
                stmt.setTime(3, entity.startTime)
                stmt.setTime(4, entity.endTime)
                stmt.setString(5, entity.group)


                stmt.executeUpdate()

                stmt.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        entity.id = generatedKeys.getLong(1)
                    } else {
                        throw SQLException("Creating user failed, no ID obtained.")
                    }
                }
            }
        }
        return entity

    }

    override fun find(weeks: Weeks?, startTime: Time?, endTime: Time?, aud: Auditorium?, building: Int?, floor: Int?): List<Lesson> {
        val initQuery = "SELECT l.id, l.auditorium, l.weeks, l.start_time, l.end_time, l.group FROM $tableName as l"

        val condition: String = if(floor == null && building == null)
            "WHERE"
        else
            "INNER JOIN auditoriums as a ON l.auditorium = a.id AND "

        val conditions
                = mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()


        if(weeks != null) conditions.add(
                (if(weeks.isSingleDay()) "l.weeks & ? != 0" else "l.weeks = ?")
                        to { stmt, index -> stmt.setInt(index, weeks.value) }
        )

        if(startTime != null) conditions.add(
                "l.start_time >= ?" to { stmt, index -> stmt.setTime(index, startTime)}
        )

        if(endTime != null) conditions.add(
                "l.end_time <= ?" to {stmt, index -> stmt.setTime(index, endTime)}
        )

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

                return extractLessons(stmt)
            }
        }
    }

    /*
    override fun findByAud(aud: Auditorium): List<Lesson> {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE auditorium = ?")
            statement.use { stmt ->
                stmt.setLong(1, aud.id)
                return extractLessons(stmt)
            }
        }
    }

    override fun findBetweenTimes(time1: Time, time2: Time): List<Lesson> {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE start_date >= ? AND and_date <= ?")
            statement.use {stmt ->
                stmt.setTime(1, time1)
                stmt.setTime(2, time2)
                return extractLessons(stmt)
            }
        }
    }*/

    override fun delete(entity: Lesson) {
        connection.use {
            val statement = it.prepareStatement("DELETE FROM $tableName WHERE id = ${entity.id}")
            statement.use {
                stmt -> stmt.execute()
            }
        }
    }

    override fun findAll(): List<Lesson> {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName")
            statement.use {stmt ->
                return extractLessons(stmt)
            }
        }
    }

    override fun findById(id: Long): Lesson? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM `$tableName` WHERE `id` = ?")
            statement.use {stmt ->
                stmt.setLong(1, id)
                return extractLessons(stmt).getOrNull(0)
            }
        }
    }

    override fun update(entity: Lesson): Lesson {
        connection.use {
            val statement = it.prepareStatement("UPDATE `$tableName` SET `auditorium` = ?, " +
                    "`weeks` = ?, `start_time` = ?, `end_time` = ?, `group` = ? WHERE `id` = ?")
            statement.use {stmt ->
                stmt.setLong(1, entity.aud.id)
                stmt.setInt(2, entity.weeks.value)
                stmt.setTime(3, entity.startTime)
                stmt.setTime(4, entity.endTime)
                stmt.setString(5, entity.group)

                stmt.setLong(6, entity.id)

                stmt.execute()
            }
        }

        return entity
    }

    /*
    override fun  findByWeek(weeks: Weeks): List<Lesson> {
        connection.use {


            val statement = if(weeks.weeks.size == 1 && !weeks.weeks.contains(WeekNumber.WEEK_ANY)) {
                it.prepareStatement("SELECT * FROM `$tableName` WHERE `weeks` & ? != 0")
            } else {
                it.prepareStatement("SELECT * FROM `$tableName` WHERE `weeks` = ?")
            }
            statement.use {stmt ->
                stmt.setInt(1, weeks.value)
                return extractLessons(stmt)
            }

        }
    }*/

    private fun extractLessons(stmt: PreparedStatement): List<Lesson> {
        val resultSet: ResultSet = stmt.executeQuery()

        val sequence = generateSequence<Lesson> {
            val aud = Auditorium( // TODO: FIX IT
                    name = "Test",
                    type = LessonType.LESSON_LAB,
                    floor = 1,
                    building = 2
            )


            if(!resultSet.next()) {
                return@generateSequence(null)
            }
            Lesson(
                    id = resultSet.getLong("id"),
                    aud = aud,
                    weeks = Weeks(resultSet.getInt("weeks")),
                    startTime = resultSet.getTime("start_time"),
                    endTime = resultSet.getTime("end_time"),
                    group = resultSet.getString("group")
            )
        }

        return sequence.toList()
    }

}

