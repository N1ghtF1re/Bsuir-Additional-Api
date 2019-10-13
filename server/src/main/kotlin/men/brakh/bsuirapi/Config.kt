package men.brakh.bsuirapi

import men.brakh.bsuirapi.app.auds.service.FreeAudsService
import men.brakh.bsuirapi.app.education.service.EducationService
import men.brakh.bsuirapi.app.education.service.impl.EducationServiceImpl
import men.brakh.bsuirapi.app.news.service.NewsService
import men.brakh.bsuirapi.app.news.service.impl.NewsServiceImpl
import men.brakh.bsuirapi.app.users.service.UserService
import men.brakh.bsuirapi.app.users.service.impl.UserServiceImpl
import men.brakh.bsuirapi.inrfastructure.authorization.AuthenticationManager
import men.brakh.bsuirapi.inrfastructure.authorization.PasswordEncrypter
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.factories.AccessJwtTokensFactory
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapi.inrfastructure.bsuirapiinitializer.BsuirApiInitializer
import men.brakh.bsuirapi.inrfastructure.db.DbInitializer
import men.brakh.bsuirapi.inrfastructure.db.dbconnection.ConnectionFactory
import men.brakh.bsuirapi.inrfastructure.db.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.inrfastructure.keys.KeysInitializer
import men.brakh.bsuirapi.repository.*
import men.brakh.bsuirapi.repository.impl.*
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*


object Config {
    val authenticationManager: AuthenticationManager = AuthenticationManager
    private val logger = LoggerFactory.getLogger(Config::class.java)

    val auditoriumRepository: AuditoriumRepository = MysqlAuditoriumRepository()
    val lessonsRepository: LessonRepository = MysqlLessonRepository(auditoriumRepository)
    val newsSourceRepository: NewsSourceRepository = MysqlNewsSourceRepository()
    val newsRepository: NewsRepository = MysqlNewsRepository(newsSourceRepository)
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

    /* SERVICES */

    lateinit var bsuirApi: BsuirApi


    lateinit var newsService: NewsService
    lateinit var  educationService: EducationService
    lateinit var  freeAudsService: FreeAudsService
    lateinit var  userService: UserService

    private fun initServices() {
        bsuirApi = BsuirApi(bsuirApiHost, passwordEncrypter)
        newsService = NewsServiceImpl(newsRepository, newsSourceRepository)
        educationService =  EducationServiceImpl(bsuirApi, authenticationManager, userRepository)
        freeAudsService = FreeAudsService(auditoriumRepository, lessonsRepository, bsuirApi)
        userService = UserServiceImpl(bsuirApi, authenticationManager, userRepository,
                passwordEncrypter, accessJwtTokenFactory)
    }

    fun getConfigProps(): Properties {
        val propsPath: String = this.javaClass.classLoader.getResource("config.properties")?.path
                ?: throw FileNotFoundException("Config not found")

        val configProps = Properties()
        configProps.load(FileInputStream(propsPath))

        return configProps
    }

    init {
        this.passwordEncrypter = PasswordEncrypter(KeysInitializer.initPasswordKey())
        this.accessJwtTokenFactory = AccessJwtTokensFactory(key = KeysInitializer.initJwtKey(),
                userRepository = userRepository)

        DbInitializer.init()

        val configProps = getConfigProps()
        bsuirApiHost = configProps.getProperty("bsuir.api.host")
        newsAtPage = configProps.getProperty("default.news.at.page").toInt()

        initServices()
        BsuirApiInitializer.initLessonsAndAuditoriums(auditoriumRepository, lessonsRepository)
    }
}