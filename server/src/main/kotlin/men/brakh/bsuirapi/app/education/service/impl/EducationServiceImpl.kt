package men.brakh.bsuirapi.app.education.service.impl

import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.app.education.service.EducationService
import men.brakh.bsuirapi.inrfastructure.authorization.AuthenticationManager
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapi.repository.UserRepository
import men.brakh.bsuirapicore.model.data.DiplomaInfo
import men.brakh.bsuirapicore.model.data.GroupInfo
import men.brakh.bsuirapicore.model.data.RecordBook
import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest

class EducationServiceImpl(private val bsuirApi: BsuirApi,
                           private val authenticationManager: AuthenticationManager,
                           private val userRepository: UserRepository) : EducationService {

    private fun getCurrentUserCrenditals(): UserAuthorizationRequest {
        val currentUserLogin = authenticationManager.getCurrentUserLogin()
                ?: throw UnauthorizedException()
        return userRepository.find(login = currentUserLogin)!!
    }

    override fun getCurrentUserDimploma(): DiplomaInfo {
        val userCredentials = getCurrentUserCrenditals()
        return bsuirApi.getDiploma(userAuthorizationRequest = userCredentials).toDiplomaInfoObject()
    }

    override fun getCurrentUserGroupInfo(): GroupInfo {
        val userCredentials = getCurrentUserCrenditals()
        return bsuirApi.getGroupInfo(userAuthorizationRequest = userCredentials).toGroupInfoObject()
    }

    override fun getCurrentUserRecordBook(): RecordBook {
        val userCredentials = getCurrentUserCrenditals()
        return bsuirApi.getMarkBook(userAuthorizationRequest = userCredentials).toRecordBookObject()
    }
}