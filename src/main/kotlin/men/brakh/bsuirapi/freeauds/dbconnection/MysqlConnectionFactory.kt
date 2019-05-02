package men.brakh.bsuirapi.freeauds.dbconnection

import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*


object MysqlConnectionFactory: ConnectionFactory {
    private val driverName: String
    private val username: String
    private val password: String
    private val url: String

    private val logger = LoggerFactory.getLogger(this.javaClass)

    init {
        // Получаем ресурсы
        val propsPath: String = this.javaClass.classLoader.getResource("db.properties")?.path ?: throw FileNotFoundException("Config not found")

        val dbProps = Properties()
        dbProps.load(FileInputStream(propsPath))

        driverName = dbProps.getProperty("db.driver.name")
        username = dbProps.getProperty("db.username")
        password = dbProps.getProperty("db.password")
        url = dbProps.getProperty("db.url")


        val tablesPath: String? = this.javaClass.classLoader.getResource("tables.sql")?.path

        if(tablesPath != null) {
            val sql = String(Files.readAllBytes(Paths.get(tablesPath)))

            try {
                val con: Connection = getConnection()



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

    override fun getConnection(): Connection {
        Class.forName(driverName)
        return DriverManager.getConnection(url, username, password)
    }
}