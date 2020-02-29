package men.brakh.bsuirstudent.domain.iis.faculty

import men.brakh.bsuirstudent.domain.BaseEntity
import javax.persistence.*

@Entity(name = "faculty")
data class Faculty(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val name: String,
    val alias: String

) : BaseEntity<Int> {

    @OneToMany(
        mappedBy = "faculty",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    lateinit var specialities: List<Speciality>
}

@Entity(name = "speciality")
data class Speciality(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val iisId: Int,

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    val faculty: Faculty,

    val name: String,

    val alias: String,

    @Enumerated(EnumType.STRING)
    val educationForm: EducationForm
) : BaseEntity<Int>

enum class EducationForm {
    EXTRAMURAL,
    FULLTIME,
    DISTANCE,
    UNKNOWN
}