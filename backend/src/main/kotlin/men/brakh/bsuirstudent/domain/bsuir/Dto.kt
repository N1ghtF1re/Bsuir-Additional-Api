package men.brakh.bsuirstudent.domain.bsuir

data class AuthorizationDto(val loggedIn: Boolean, val username: String?, val fio: String?, val message: String?)

data class SkillDto(val id: Int, val name: String)

data class ReferenceDto(val id: Int, val name: String, val reference: String)

data class PersonalCVDto(val id: Int,
                         val firstName: String,
                         val lastName: String,
                         val middleName: String,
                         val birthDate: String,
                         val photoUrl: String?,
                         val summary: String?,
                         val rating: Int,
                         val faculty: String,
                         val cource: Int,
                         val speciality: String,
                         val studentGroup: String,

                         val showRating: Boolean,
                         val published: Boolean,
                         val searchJob: Boolean,
                         val skills: List<SkillDto>,
                         val references: List<ReferenceDto>,
                         override var username: String?
) : UsernameAware