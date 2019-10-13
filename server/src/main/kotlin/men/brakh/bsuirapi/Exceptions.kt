package men.brakh.bsuirapi

class UnauthorizedException(msg: String): RuntimeException(msg) {
    constructor(): this("Unauthorized")
}

open class NotFoundException(msg: String): RuntimeException(msg)

class UserNotFoundException(msg: String): NotFoundException(msg) {
    constructor(): this("UserAuthorizationRequest not found")
}