package men.brakh.bsuirapi.servlets.basic

class Parameters(private val map: Map<String, String>) {
    fun getRequired(key: String): String {
        return map[key] ?: throw IllegalArgumentException("Parameter $key is required")
    }

    operator fun get(key: String): String? {
        return map[key]
    }

    operator fun contains(key: String): Boolean {
        return key in map
    }
}