package men.brakh.bsuirstudent.application.bsuir

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

data class GroupInfoStudentBsuirDto(val position: String, val fio: String, val phone: String, val email: String)

data class GroupInfoBsuirDto(val numberGroup: String, val groupInfoStudentDto: List<GroupInfoStudentBsuirDto>)

data class AuditoriumTypeBsuirDto(val name: String, val abbrev: String)

data class BuildingBsuirDto(val id: Int, val name: String)

data class AuditoriumBsuirDto(
    val name: String,
    val auditoryType: AuditoriumTypeBsuirDto,
    val buildingNumber: BuildingBsuirDto
)

data class ScheduleBsuirGroupDto(val id: Int, val name: String, val course: Int?)


data class ScheduleBsuirDto(val weekNumber: List<Int>, val studentGroup: List<String>, val numSubgroup: Int,
                       val auditory: List<String>, val startLessonTime: String, val endLessonTime: String,
                       val subject: String, val note: String?, val lessonType: String)

data class DayScheduleBsuirDto(val weekDay: String, val schedule: List<ScheduleBsuirDto>)

data class ScheduleResponseBsuirDto(val schedules: List<DayScheduleBsuirDto>)

data class FacultyBsuirDto(val name: String, val abbrev: String, val id: Int)

data class EducationFormBsuirDto(val name: String, val id: Int)

data class SpecialityBsuirDto(val id: Int, val name: String, val abbrev: String,
                         val educationForm: EducationFormBsuirDto, val facultyId: Int,
                         val code: String)

data class RatingBsuirDto(val studentCardNumber: String, val average: Double?, val hours: Int?, val fistAverage: Double?,
                     val firstHours: Int?, val secondAverage: Double?, val secondHours: Int?, val thirdAverage: Double?,
                     val thirdHours: Int?, val averageShift: Double?)