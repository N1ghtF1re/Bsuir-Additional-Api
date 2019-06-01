package men.brakh.bsuirapi.dbconnection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}