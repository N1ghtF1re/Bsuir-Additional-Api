package men.brakh.bsuirapi.inrfastructure.authorization

/**
 * Singleton for authentication management. It's storing user logins for current context
 * (HTTP Servlet guarantee that the whole request will be managed in one thread)
 */
object AuthenticationManager {
    private val userLoginThreadLocal: ThreadLocal<String?> = ThreadLocal()
    private val permissionsThreadLocal: ThreadLocal<List<Permission>?> = ThreadLocal()


    fun authenticate(userLogin: String, permissions: List<Permission>? = null) {
        userLoginThreadLocal.set(userLogin)
        permissionsThreadLocal.set(permissions)
    }

    fun removeAuthentication() {
        userLoginThreadLocal.remove()
        permissionsThreadLocal.remove()
    }

    fun isAuthenticated(): Boolean {
        return userLoginThreadLocal.get() != null
    }

    fun getCurrentUserLogin(): String? {
        return userLoginThreadLocal.get()
    }

    fun isGranded(permission: Permission): Boolean {
        if (permissionsThreadLocal.get() == null) return false

        return permission in permissionsThreadLocal.get()!!
    }
}