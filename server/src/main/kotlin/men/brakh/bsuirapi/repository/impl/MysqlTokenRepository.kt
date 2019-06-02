package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapicore.model.data.Token
import men.brakh.bsuirapi.repository.TokenRepository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlTokenRepository (tablename: String) : MysqlRepository<Token>(tablename), TokenRepository {
    constructor() : this("tokens")

    override fun extractor(resultSet: ResultSet): Token? {
        return Token(
                token = resultSet.getString("token"),
                description = resultSet.getString("description")
        )
    }

    override fun add(entity: Token): Token {
        connection.use { connection ->
            val statement: PreparedStatement
                    = connection.prepareStatement("INSERT INTO `$tableName` (`token`, `description`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use { stmt ->
                stmt.setString(1, entity.token)
                stmt.setString(2, entity.description)

                stmt.executeUpdate()

                return entity.copy(id = stmt.generatedId)
            }
        }
    }

    override fun update(entity: Token): Token {
        connection.use {
            val statement =
                    it.prepareStatement("UPDATE $tableName SET token = ?, description = ? WHERE id = ?")
            statement.use { stmt ->
                stmt.setString(1, entity.token)
                stmt.setString(2, entity.description)
                stmt.setLong(3, entity.id)

                stmt.execute()
                return entity
            }
        }
    }

    override fun find(token: String): Token? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE token = ?")
            statement.use {stmt ->
                stmt.setString(1, token)
                return extract(stmt).getOrNull(0)
            }
        }
    }

}
