package men.brakh.bsuirstudent.domain.bsuir

data class AuthorizationBsuirDto(val loggedIn: Boolean, val username: String?, val fio: String?, val message: String?)

data class SkillBsuirDto(val id: Int, val name: String)

data class ReferenceBsuirDto(val id: Int, val name: String, val reference: String)

data class PersonalCVBsuirDto(val id: Int,
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
                              val skills: List<SkillBsuirDto>,
                              val references: List<ReferenceBsuirDto>,
                              override var username: String?
) : UsernameAware

// RecordBook
data class DiplomaBsuirDto(val name: String?, val theme: String?)

data class MarkBsuirDto(val subject: String,
                        val formOfControl: String?,
                        val hours: String,
                        val mark: String,
                        val date: String,
                        val teacher: String?,
                        val commonMark: Double?,
                        val commonRetakes: Double?,
                        val retakesCount: Int,
                        val idSubject: Int,
                        val idFormOfControl: Int
)

data class MarkPageBsuirDto(val averageMark: Double, val marks: List<MarkBsuirDto>) {
    fun isEmpty() = marks.isEmpty()
}

data class MarkBookBsuirDto(val number: String,
                            val averageMark: Double,
                            val markPages: Map<String, MarkPageBsuirDto>)