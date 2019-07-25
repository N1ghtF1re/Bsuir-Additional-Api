package men.brakh.bsuirapicore.model.data

data class GroupMate(val role: String?, val name: String, val email: String?, val phone: String?)

data class GroupInfo(val name: String, val members: List<GroupMate>)