package men.brakh.bsuirapicore.model.data


data class Faculty(
        val id: Int,
        val name: String,
        val alias: String,
        val specialities: List<Speciality>
)

data class Speciality(
    val id: Int,
    val name: String,
    val alias: String,
    val educationForm: EducationForm
)

enum class EducationForm {
    EXTRAMURAL,
    FULLTIME,
    DISTANCE,
    UNKNOWN
}