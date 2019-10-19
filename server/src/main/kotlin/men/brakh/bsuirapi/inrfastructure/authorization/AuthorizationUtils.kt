package men.brakh.bsuirapi.inrfastructure.authorization

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.app.users.service.UserService
import men.brakh.bsuirapicore.model.data.User

inline fun authorized(block: (user: User) -> Unit) {
    val userService: UserService = Config.userService

    val user = userService.getAuthenticatedUser()
    block(user)
}