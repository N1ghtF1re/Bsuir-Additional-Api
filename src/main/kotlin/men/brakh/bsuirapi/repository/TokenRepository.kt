package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.model.data.Token


interface TokenRepository {
    fun find(token: String): Token?
}