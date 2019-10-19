package men.brakh.bsuirapi.app.users.service.impl

import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.UserNotFoundException
import men.brakh.bsuirapi.app.users.service.UserService
import men.brakh.bsuirapi.inrfastructure.authorization.AuthenticationManager
import men.brakh.bsuirapi.inrfastructure.authorization.PasswordEncrypter
import men.brakh.bsuirapi.inrfastructure.authorization.jwt.factories.AccessJwtTokensFactory
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.User
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest
import men.brakh.bsuirapicore.model.data.UserSettings
import java.util.*

class UserServiceImpl(private val bsuirApi: BsuirApi,
                      private val authenticationManager: AuthenticationManager,
                      private val userRepository: UserRepository,
                      private val passwordEncrypter: PasswordEncrypter,
                      private val accessJwtTokensFactory: AccessJwtTokensFactory) : UserService {

    private fun getCurrentUserCrenditals(): UserAuthorizationRequest {
        val login = authenticationManager.getCurrentUserLogin() ?: throw UnauthorizedException()
        return userRepository.find(login)!!
    }

    override fun changeUserSettings(userSettings: UserSettings) {
        val userCredentials = getCurrentUserCrenditals()

        bsuirApi.savePublished(userCredentials, userSettings.isPublicProfile)
        bsuirApi.saveSearchJob(userCredentials, userSettings.isSearchJob)
        bsuirApi.saveShowRating(userCredentials, userSettings.isShowRating)
    }

    override fun getAuthenticatedUser(): User {
        val userCredentials = getCurrentUserCrenditals()
        return bsuirApi.getPersonalCV(authorizedUserAuthorizationRequest = userCredentials).toUserInfoObject()
    }

    override fun getUserById(id: Int): User {
        return bsuirApi.getUserCV(id).toUserInfoObject()
    }

    override fun getCurrentUserSettings(): UserSettings {
        val userCredentials = getCurrentUserCrenditals()
        return bsuirApi.getPersonalCV(authorizedUserAuthorizationRequest = userCredentials).toUserSettings()
    }

    private fun nexAugust(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        calendar.set(Calendar.MONTH, 7)
        calendar.add(Calendar.YEAR, 1)
        return calendar.time
    }

    override fun getTokenForUser(login: String, password: String): String {
        bsuirApi.tryAuth(login, password) ?: throw IllegalArgumentException("invalid login or password")

        val encryptedPassword = passwordEncrypter.encrypt(password)

        val user = UserAuthorizationRequest(login = login, password = encryptedPassword)

        val dbUser = userRepository.find(login=login)
        if(dbUser == null) {
            userRepository.add(user)
        } else {
            userRepository.update(dbUser.copy(password = user.password))
        }

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 0)
        calendar.set(Calendar.MONTH, 9)
        calendar.add(Calendar.YEAR, 1)

        return accessJwtTokensFactory.createToken(user, nexAugust())
    }

}