package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.model.data.Auditorium
import men.brakh.bsuirapi.model.data.LessonType
import men.brakh.bsuirapi.repository.AuditoriumRepository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlAuditoriumRepository(tableName: String) :
        MysqlRepository<Auditorium>(tableName), AuditoriumRepository {

    constructor() : this("auditoriums")


    override fun extractor(resultSet: ResultSet): Auditorium? {
        return Auditorium(
                id = resultSet.getLong("id"),
                name = resultSet.getString("name"),
                floor = resultSet.getInt("floor"),
                building = resultSet.getInt("building"),
                type = LessonType.valueOf(resultSet.getString("type"))
        )
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

                return entity.copy(id = stmt.generatedId)
            }
        }
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

                return extract(stmt)
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

}