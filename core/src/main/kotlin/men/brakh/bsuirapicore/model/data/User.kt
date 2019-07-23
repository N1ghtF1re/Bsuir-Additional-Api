package men.brakh.bsuirapicore.model.data

data class User(override val id: Long = -1, val login: String, val password: String): Identifiable
