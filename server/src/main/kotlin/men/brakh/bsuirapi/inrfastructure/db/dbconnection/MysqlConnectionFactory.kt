package men.brakh.bsuirapi.inrfastructure.db.dbconnection

import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.sql.Connection
import java.sql.DriverManager
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
    }

    override fun getConnection(): Connection {
        Class.forName(driverName)
        return DriverManager.getConnection(url, username, password)
    }
}