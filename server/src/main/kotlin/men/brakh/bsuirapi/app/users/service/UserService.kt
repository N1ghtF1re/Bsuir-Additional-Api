package men.brakh.bsuirapi.app.users.service

import men.brakh.bsuirapicore.model.data.User
import men.brakh.bsuirapicore.model.data.UserSettings

interface UserService {
    fun getAuthenticatedUser(): User
    fun getUserById(id: Int): User
    fun getCurrentUserSettings(): UserSettings
    fun changeUserSettings(userSettings: UserSettings)

    fun getTokenForUser(login: String, password: String): String
}