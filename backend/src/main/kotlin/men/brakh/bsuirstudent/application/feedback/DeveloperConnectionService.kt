package men.brakh.bsuirstudent.application.feedback


interface DeveloperConnectionService {
    fun handleError(ex: Throwable, msg: String? = null)
    fun write(s: String)
}