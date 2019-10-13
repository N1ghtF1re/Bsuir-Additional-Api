package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.inrfastructure.authorization.Permission
import men.brakh.bsuirapi.inrfastructure.authorization.servicetoken.ServiceToken
import men.brakh.bsuirapi.repository.TokenRepository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlTokenRepository (tablename: String) : MysqlRepository<ServiceToken>(tablename), TokenRepository {
    constructor() : this("tokens")

    override fun extractor(resultSet: ResultSet): ServiceToken? {
        return ServiceToken(
                token = resultSet.getString("token"),
                description = resultSet.getString("description"),
                id = resultSet.getLong("id"),
                permissions = getPermissions(resultSet.getLong("id"))
        )
    }

    private fun getPermissions(id: Long): List<Permission> {
        connection.use { connection ->
            val statement = connection.prepareStatement("SELECT * FROM `permissions` WHERE token_id = ?")
            statement.use {stmt ->
                stmt.setLong(1, id)
                val resultSet = stmt.executeQuery()

                val sequence: Sequence<Permission> = generateSequence {
                    if(!resultSet.next()) {
                        return@generateSequence(null)
                    }

                    Permission.valueOf(resultSet.getString("permission"))
                }

                return sequence.toList()
            }
        }
    }

    override fun add(entity: ServiceToken): ServiceToken {
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

    override fun update(entity: ServiceToken): ServiceToken {
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

    override fun find(token: String): ServiceToken? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE token = ?")
            statement.use {stmt ->
                stmt.setString(1, token)
                return extract(stmt).getOrNull(0)
            }
        }
    }

}
