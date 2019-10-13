package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.inrfastructure.authorization.servicetoken.ServiceToken


interface TokenRepository {
    fun find(token: String): ServiceToken?
}