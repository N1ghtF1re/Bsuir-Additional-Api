package men.brakh.bsuirapi.repository

import men.brakh.bsuirapicore.model.data.Token


interface TokenRepository {
    fun find(token: String): Token?
}