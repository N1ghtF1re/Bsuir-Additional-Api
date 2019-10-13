package men.brakh.bsuirapi.inrfastructure.db

import men.brakh.bsuirapi.inrfastructure.db.dbconnection.MysqlConnectionFactory
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

object DbInitializer {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun init() {
        val tablesPath: String? = this.javaClass.classLoader.getResource("tables.sql")?.path

        if(tablesPath != null) {
            val sql = String(Files.readAllBytes(Paths.get(tablesPath)))

            try {
                val con: Connection = MysqlConnectionFactory.getConnection()



                con.use {
                    val stmt: Statement = it.createStatement()

                    stmt.use {
                        sql.split(";").forEach { sqlPart: String ->
                            if(sqlPart.isNotEmpty())
                                stmt.execute("$sqlPart;")
                        }
                    }
                }
            } catch (e: SQLException) {
                logger.error("ERROR CONNECTING TO DB", e)
            }
        }

        logger.info("MYSQL CONNECTION INITIALIZED")
    }
}