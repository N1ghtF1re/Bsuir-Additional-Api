package men.brakh.bsuirapicore.model.data

import java.util.*

data class User(override val id: Long = -1, val login: String, val password: String): Identifiable

data class StudyInfo(
        val faculty: String,
        val course: Int,
        val speciality: String,
        val studentGroup: String
)

data class Reference(val name: String, val reference: String)

data class UserInfo(val id: Int,
                    val firstName: String,
                    val lastName: String,
                    val middleName: String,
                    val birthDay: Date,
                    val photo: String?,
                    val summary: String?,
                    val rating: Int,

                    val education: StudyInfo,

                    val skills: List<String>,
                    val references: List<Reference>,

                    val settings: UserSettings
)

data class UserSettings(val isShowRating: Boolean, val isPublicProfile: Boolean, val isSearchJob: Boolean)