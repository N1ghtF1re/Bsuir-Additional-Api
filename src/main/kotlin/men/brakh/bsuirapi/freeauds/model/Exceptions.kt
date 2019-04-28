package men.brakh.bsuirapi.freeauds.model

class AuditoriumNotFoundException(msg: String): RuntimeException(msg) {
    constructor() : this("Auditorium Not Found")
}