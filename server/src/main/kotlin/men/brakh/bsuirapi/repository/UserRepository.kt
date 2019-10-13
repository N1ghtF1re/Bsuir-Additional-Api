package men.brakh.bsuirapi.repository

import men.brakh.bsuirapicore.model.data.UserAuthorizationRequest

interface UserRepository: Repository<UserAuthorizationRequest> {
    fun find(login: String): UserAuthorizationRequest?
}