package men.brakh.bsuirapi

class UnauthorizedException(msg: String): RuntimeException(msg) {
    constructor(): this("Unauthorized")
}

class EntityAlreadyExistsException(msg: String): RuntimeException(msg) {
    constructor(): this("Entity already exists")
}

class AccessDeniedException(msg: String): RuntimeException(msg) {
    constructor(): this("Access denied")
}

open class NotFoundException(msg: String): RuntimeException(msg)

class UserNotFoundException(msg: String): NotFoundException(msg) {
    constructor(): this("UserAuthorizationRequest not found")
}