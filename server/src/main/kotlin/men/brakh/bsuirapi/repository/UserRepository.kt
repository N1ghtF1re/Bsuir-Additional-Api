package men.brakh.bsuirapi.repository

import men.brakh.bsuirapicore.model.data.User

interface UserRepository: Repository<User> {
    fun find(login: String): User?
}