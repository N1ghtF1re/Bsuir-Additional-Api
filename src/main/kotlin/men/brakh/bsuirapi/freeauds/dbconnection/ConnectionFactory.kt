package men.brakh.bsuirapi.freeauds.dbconnection

import java.sql.Connection

interface ConnectionFactory {
    fun getConnection(): Connection
}