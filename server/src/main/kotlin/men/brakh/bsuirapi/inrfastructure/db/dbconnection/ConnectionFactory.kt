package men.brakh.bsuirapi.inrfastructure.db.dbconnection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}