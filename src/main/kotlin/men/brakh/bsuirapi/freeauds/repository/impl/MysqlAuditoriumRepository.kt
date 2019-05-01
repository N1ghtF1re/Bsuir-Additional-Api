package men.brakh.bsuirapi.freeauds.repository.impl

import men.brakh.bsuirapi.freeauds.Config
import men.brakh.bsuirapi.freeauds.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.freeauds.model.Auditorium
import men.brakh.bsuirapi.freeauds.model.LessonType
import men.brakh.bsuirapi.freeauds.repository.AuditoriumRepository
import java.sql.*

class MysqlAuditoriumRepository(private val tableName: String)  : AuditoriumRepository{
    constructor() : this("auditoriums")

    private val conFactory: ConnectionFactory by lazy { Config.connectionFactory }

    private val connection: Connection
        get() {
            return conFactory.getConnection()
        }


    override fun add(entity: Auditorium): Auditorium {
        connection.use {
            val statement = it.prepareStatement("INSERT INTO $tableName " +
                    "(name, type, floor, building) VALUES(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use {stmt ->
                stmt.setString(1, entity.name)
                stmt.setString(2, entity.type.name)
                stmt.setInt(3, entity.floor)
                stmt.setInt(4, entity.building)


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

    override fun add(entities: List<Auditorium>) {
        entities.forEach{add(it)}
    }

    override fun find(building: Int?, floor: Int?, name: String?, type: LessonType?): List<Auditorium> {
        val initQuery = "SELECT * FROM $tableName WHERE"

        val conditions
                = mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()

        if(building != null) conditions.add(
                "building = ?" to {stmt, index -> stmt.setInt(index, building)}
        )

        if(name != null) conditions.add(
                "name = ?" to {stmt, index -> stmt.setString(index, name)}
        )

        if(floor != null) conditions.add(
                "floor = ?" to {stmt, index -> stmt.setInt(index, floor)}
        )

        if(type != null) conditions.add(
                "type = ?" to {stmt, index -> stmt.setString(index, type.name)}
        )

        if(conditions.size == 0) return findAll()

        val finalQuery = "$initQuery ${conditions.map{it.first}.joinToString(separator = " AND ")}"

        connection.use {
            val statement = it.prepareStatement(finalQuery)
            statement.use { stmt ->
                for((index, cond) in conditions.withIndex()) {
                    cond.second(stmt, index + 1) // Установка параметров запроса
                }

                return extractAuditorium(stmt)
            }
        }
    }

    override fun delete(entity: Auditorium) {
        connection.use {
            val statement = it.prepareStatement("DELETE FROM $tableName WHERE id = ?")
            statement.use { stmt ->
                stmt.setLong(1, entity.id)
            }
        }
    }

    override fun findAll(): List<Auditorium> {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName")
            statement.use {stmt ->
                return extractAuditorium(stmt)
            }
        }
    }

    override fun findById(id: Long): Auditorium? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE id = ?")
            statement.use {stmt ->
                stmt.setLong(1, id)
                return extractAuditorium(stmt).getOrNull(0)
            }
        }
    }

    override fun update(entity: Auditorium): Auditorium {
        connection.use {
            val statement =
                    it.prepareStatement("UPDATE $tableName SET type = ?, name = ?, floor = ?, building = ? WHERE id = ?")
            statement.use { stmt ->
                stmt.setString(1, entity.type.name)
                stmt.setString(2, entity.name)
                stmt.setInt(3, entity.floor)
                stmt.setInt(4, entity.building)
                stmt.setLong(5, entity.id)

                stmt.execute()
                return entity
            }
        }
    }

    private fun extractAuditorium(stmt: PreparedStatement): List<Auditorium> {
        val resultSet: ResultSet = stmt.executeQuery()

        val sequence = generateSequence<Auditorium> {
            if(!resultSet.next()) {
                return@generateSequence(null)
            }
            Auditorium(
                    id = resultSet.getLong("id"),
                    name = resultSet.getString("name"),
                    floor = resultSet.getInt("floor"),
                    building = resultSet.getInt("building"),
                    type = LessonType.valueOf(resultSet.getString("type"))
            )
        }

        return sequence.toList()
    }

}