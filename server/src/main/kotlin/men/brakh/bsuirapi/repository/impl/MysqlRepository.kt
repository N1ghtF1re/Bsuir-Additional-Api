package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.inrfastructure.db.dbconnection.ConnectionFactory
import men.brakh.bsuirapicore.model.data.Identifiable
import men.brakh.bsuirapi.repository.Repository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


abstract class MysqlRepository<T : Identifiable>(val tableName: String) : Repository<T> {
    private val conFactory: ConnectionFactory by lazy { Config.connectionFactory }
    protected val connection: Connection
        get() {
            return conFactory.getConnection()
        }

    /**
     * Function which transforms ResultSet into entity
     */
    protected abstract fun extractor(resultSet: ResultSet): T?

    /**
     * Executes statement, iterates resultSet, after that calling extractor for formations entities list
     * @see MysqlRepository#extractor
     */
    protected fun extract(stmt: PreparedStatement): List<T> {
        val resultSet: ResultSet = stmt.executeQuery()

        val sequence: Sequence<T> = generateSequence {
            if(!resultSet.next()) {
                return@generateSequence(null)
            }

            extractor(resultSet)
        }

        return sequence.toList()
    }


    override fun delete(entity: T) {
        connection.use {
            val statement = it.prepareStatement("DELETE FROM $tableName WHERE id = ?")
            statement.use { stmt ->
                stmt.setLong(1, entity.id)
                stmt.execute();
            }
        }
    }

    override fun findAll(): List<T> {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName")
            statement.use {stmt ->
                return extract(stmt)
            }
        }
    }

    override fun findById(id: Long): T? {
        connection.use {
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE id = ?")
            statement.use {stmt ->
                stmt.setLong(1, id)
                return extract(stmt).getOrNull(0)
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


val PreparedStatement.generatedId: Long
    get() = generatedKeys.use { generatedKeys ->
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1)
        } else {
            throw SQLException("Creating user failed, no ID obtained.")
        }
    }