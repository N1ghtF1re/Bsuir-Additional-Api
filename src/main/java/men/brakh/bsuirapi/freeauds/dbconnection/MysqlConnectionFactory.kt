package men.brakh.bsuirapi.freeauds.dbconnection

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
                        stmt.execute(sql)
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        println("MYSQL INITIALIZED")
    }

    override fun getConnection(): Connection {
        Class.forName(driverName)
        return DriverManager.getConnection(url, username, password)
    }
}