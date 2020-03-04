package men.brakh.bsuirstudent.domain.iis.student

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.application.bsuir.BsuirEntity
import java.util.*
import javax.persistence.*

@Entity(name = "student_skill")
data class StudentSkill(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    val iisId: Int,
    val name: String
): BaseEntity<Int>

@Entity(name = "student_reference")
data class StudentReference(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    val iisId: Int,
    val name: String,
    val reference: String
) : BaseEntity<Int>

@Entity(name = "student_education_information")
data class EducationInformation  (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    val faculty: String,
    val course: Int,
    val speciality: String,

    @Column(name = "`group`")
    val group: String
) : BaseEntity<Int>


@Entity(name = "student_settings")
data class UserSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    val isShowRating: Boolean,
    val isPublicProfile: Boolean,
    val isSearchJob: Boolean
) : BaseEntity<Int>


@Entity(name = "student")
data class Student (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @Column(unique = true)
    val iisId: Int,

    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val recordBookNumber: String?,
    val birthDay: Date,
    val photo: String?,
    val summary: String?,
    val rating: Int,

    override val updatedAt: Date
) : BaseEntity<Int>, BsuirEntity {
    @OneToOne(
        mappedBy = "student",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    @JoinColumn(name = "id")
    lateinit var educationInfo: EducationInformation

    @OneToOne(
        mappedBy = "student",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    lateinit var settings: UserSettings

    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    lateinit var references: List<StudentReference>

    @OneToMany(
        mappedBy = "student",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    lateinit var skills: List<StudentSkill>
}