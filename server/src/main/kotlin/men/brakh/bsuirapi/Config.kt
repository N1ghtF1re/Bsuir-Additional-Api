package men.brakh.bsuirapi


import men.brakh.bsuirapi.app.auds.service.FreeAudsService
import men.brakh.bsuirapi.app.education.service.EducationService
import men.brakh.bsuirapi.app.education.service.impl.EducationServiceImpl
import men.brakh.bsuirapi.app.file.service.FileService
import men.brakh.bsuirapi.app.file.service.impl.FileServiceImpl
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
import men.brakh.bsuirapi.inrfastructure.db.dbconnection.MysqlConnectionFactory
import men.brakh.bsuirapi.inrfastructure.externalStorage.service.impl.GoogleDriveExternalFilesStorageService
import men.brakh.bsuirapi.inrfastructure.keys.KeysInitializer
import men.brakh.bsuirapi.repository.*
import men.brakh.bsuirapi.repository.impl.*
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.crypto.KeyGenerator


object Config {
    val authenticationManager: AuthenticationManager = AuthenticationManager

    val auditoriumRepository: AuditoriumRepository = MysqlAuditoriumRepository()
    val lessonsRepository: LessonRepository = MysqlLessonRepository(auditoriumRepository)
    private val newsSourceRepository: NewsSourceRepository = MysqlNewsSourceRepository()
    private val newsRepository: NewsRepository = MysqlNewsRepository(newsSourceRepository)
    val tokenRepository: TokenRepository = MysqlTokenRepository()
    private val userRepository: UserRepository = MysqlUserRepository()
    private val fileRepository: FileRepository = MysqlFileRepository()

    val connectionFactory = MysqlConnectionFactory

    val newsAtPage: Int

    val bsuirApiHost: String

    var passwordEncrypter: PasswordEncrypter = PasswordEncrypter(KeysInitializer.initPasswordKey())
    var accessJwtTokenFactory: AccessJwtTokensFactory

    /* SERVICES */

    lateinit var bsuirApi: BsuirApi


    lateinit var newsService: NewsService
    lateinit var  educationService: EducationService
    lateinit var  freeAudsService: FreeAudsService
    lateinit var  userService: UserService
    private val externalFilesStorageService = GoogleDriveExternalFilesStorageService()
    lateinit var fileService: FileService

    private fun initServices() {
        bsuirApi = BsuirApi(bsuirApiHost, passwordEncrypter)
        newsService = NewsServiceImpl(newsRepository, newsSourceRepository)
        educationService =  EducationServiceImpl(bsuirApi, authenticationManager, userRepository)
        freeAudsService = FreeAudsService(auditoriumRepository, lessonsRepository, bsuirApi)
        userService = UserServiceImpl(bsuirApi, authenticationManager, userRepository,
                passwordEncrypter, accessJwtTokenFactory)

        fileService = FileServiceImpl(externalFilesStorageService, fileRepository)
    }

    private fun getConfigProps(): Properties {
        val propsPath: String = this.javaClass.classLoader.getResource("config.properties")?.path
                ?: throw FileNotFoundException("Config not found")

        val configProps = Properties()
        configProps.load(FileInputStream(propsPath))

        return configProps
    }

    init {
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