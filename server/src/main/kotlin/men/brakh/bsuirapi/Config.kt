package men.brakh.bsuirapi

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import men.brakh.bsuirapi.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.model.LessonsScheduleUpdater
import men.brakh.bsuirapi.model.PasswordEncrypter
import men.brakh.bsuirapi.model.bsuirapi.BsuirApi
import men.brakh.bsuirapi.model.jwt.factories.AccessJwtTokensFactory
import men.brakh.bsuirapi.repository.*
import men.brakh.bsuirapi.repository.impl.*
import men.brakh.bsuirapicore.model.data.Auditorium
import men.brakh.bsuirapicore.model.data.Lesson
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.crypto.KeyGenerator




object Config {
    private val logger = LoggerFactory.getLogger(Config::class.java)

    val lessonsRepository: LessonRepository = MysqlLessonRepository()
    val auditoriumRepository: AuditoriumRepository = MysqlAuditoriumRepository()
    val newsSourceRepository: NewsSourceRepository = MysqlNewsSourceRepository()
    val newsRepository: NewsRepository = MysqlNewsRepository()
    val tokenRepository: TokenRepository = MysqlTokenRepository()
    val userRepository: UserRepository = MysqlUserRepository()
    val connectionFactory: ConnectionFactory
        get() {
            return MysqlConnectionFactory
        }

    val newsAtPage: Int

    val bsuirApiHost: String

    lateinit var passwordEncrypter: PasswordEncrypter
    lateinit var accessJwtTokenFactory: AccessJwtTokensFactory

    private fun initKeys() {
        val keysFolder: URL = this::class.java.classLoader.getResource("")!!

        val keyFile = Paths.get(keysFolder.path + "aes.key")


        val secretPasswordsKey = if(Files.exists(keyFile)) {
            String(Files.readAllBytes(keyFile))
        } else {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256)

            val key = Base64.getEncoder().encodeToString(keyGenerator.generateKey().encoded)
            Files.write(keyFile, key.toByteArray())
            logger.info("Created aes key file: ${keyFile.toAbsolutePath()}")
            key
        }

        this.passwordEncrypter = PasswordEncrypter(secretPasswordsKey)

        val jwtKeyFile =  Paths.get(keysFolder.path + "jwt.key")
        val secretJwtKey = if(Files.exists(jwtKeyFile)) {
            String(Files.readAllBytes(jwtKeyFile))
        } else {
            val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
            val base64Key = Base64.getEncoder().encodeToString(key.encoded)
            Files.write(jwtKeyFile, base64Key.toByteArray())
            logger.info("Created jwt key file: ${keyFile.toAbsolutePath()}")

            base64Key
        }

        accessJwtTokenFactory = AccessJwtTokensFactory(key = secretJwtKey, userRepository = userRepository)
    }

    init {
        val propsPath: String = this.javaClass.classLoader.getResource("config.properties")?.path
                ?: throw FileNotFoundException("Config not found")

        val configProps = Properties()
        configProps.load(FileInputStream(propsPath))

        bsuirApiHost = configProps.getProperty("bsuir.api.host")
        newsAtPage = configProps.getProperty("default.news.at.page").toInt()


        if(auditoriumRepository.count() == 0) {
            logger.info("Loading the auditoriums list ... It takes a few minutes...")
            val auds: List<Auditorium> = BsuirApi.getAuditoriums()
            auditoriumRepository.add(auds)
            logger.info("Auditoriums list loaded!")
        }

        if(lessonsRepository.count() == 0) {
            logger.info("Loading the lessons list ... It takes a tens of minutes...")
            val lessons: List<Lesson> = BsuirApi.getGroups().flatMap { BsuirApi.getSchedule(it.name) }
            lessonsRepository.add(lessons)
            logger.info("Lessons list loaded!")
        }

        initKeys()
        LessonsScheduleUpdater.start()
    }
}