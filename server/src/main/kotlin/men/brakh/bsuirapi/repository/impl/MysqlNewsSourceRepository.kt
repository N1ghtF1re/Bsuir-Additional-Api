package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.model.data.NewsSource
import men.brakh.bsuirapi.repository.NewsSourceRepository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlNewsSourceRepository(tableName: String) : MysqlRepository<NewsSource>(tableName), NewsSourceRepository {
    constructor() : this("news_source")

    override fun extractor(resultSet: ResultSet): NewsSource? {
        return NewsSource(
                id = resultSet.getLong("id"),
                name = resultSet.getString("name"),
                type = resultSet.getString("type")
        )
    }


    override fun add(entity: NewsSource): NewsSource {
        connection.use { connection ->
            val statement: PreparedStatement
                    = connection.prepareStatement("INSERT INTO `$tableName` (`type`, `name`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use { stmt ->
                stmt.setString(1, entity.type)
                stmt.setString(2, entity.name)

                stmt.executeUpdate()

                return entity.copy(id = stmt.generatedId)
            }
        }
    }

    override fun update(entity: NewsSource): NewsSource {
        connection.use {
            val statement =
                    it.prepareStatement("UPDATE $tableName SET type = ?, name = ? WHERE id = ?")
            statement.use { stmt ->
                stmt.setString(1, entity.type)
                stmt.setString(2, entity.name)
                stmt.setLong(3, entity.id)

                stmt.execute()
                return entity
            }
        }
    }

    override fun find(type: String?, name: String?): List<NewsSource> {
        val initQuery = "SELECT * FROM $tableName WHERE"

        val conditions=
                mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()

        if(name != null) conditions.add(
                "name = ?" to {stmt, index -> stmt.setString(index, name)}
        )

        if(type != null) conditions.add(
                "type = ?" to {stmt, index -> stmt.setString(index, type)}
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


}
