package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlUserRepository(tableName: String) : MysqlRepository<UserAuthorizationRequest>(tableName), UserRepository {
    constructor() : this("users")

    override fun extractor(resultSet: ResultSet): UserAuthorizationRequest? {
        return UserAuthorizationRequest(
                id = resultSet.getLong("id"),
                login = resultSet.getString("login"),
                password = resultSet.getString("password")
        )
    }

    override fun add(entity: UserAuthorizationRequest): UserAuthorizationRequest {
        connection.use { connection ->
            val statement: PreparedStatement
                    = connection.prepareStatement("INSERT INTO `$tableName` (`login`, `password`) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use { stmt ->
                stmt.setString(1, entity.login)
                stmt.setString(2, entity.password)

                stmt.executeUpdate()

                return entity.copy(id = stmt.generatedId)
            }
        }
    }

    override fun update(entity: UserAuthorizationRequest): UserAuthorizationRequest {
        connection.use {
            val statement =
                    it.prepareStatement("UPDATE $tableName SET login = ?, password = ? WHERE id = ?")
            statement.use { stmt ->
                stmt.setString(1, entity.login)
                stmt.setString(2, entity.password)
                stmt.setLong(3, entity.id)

                stmt.execute()
                return entity
            }
        }
    }

    override fun find(login: String): UserAuthorizationRequest? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE login = ?")
            statement.use {stmt ->
                stmt.setString(1, login)
                return extract(stmt).getOrNull(0)
            }
        }
    }
}