package men.brakh.bsuirapicore.model.data

import java.util.*

data class UserAuthorizationRequest(override val id: Long = -1,
                                    val login: String,
                                    val password: String): Identifiable

data class StudyInfo(
        val faculty: String,
        val course: Int,
        val speciality: String,
        val group: String
)

data class Reference(val id: Int, val name: String, val reference: String)
data class Skill(val id: Int, val name: String)

data class User(val id: Int,
                val firstName: String,
                val lastName: String,
                val middleName: String,
                val birthDay: Date,
                val photo: String?,
                val summary: String?,
                val rating: Int,

                val education: StudyInfo,

                val skills: List<Skill>,
                val references: List<Reference>,

                val settings: UserSettings
)

data class UserSettings(val isShowRating: Boolean, val isPublicProfile: Boolean, val isSearchJob: Boolean)