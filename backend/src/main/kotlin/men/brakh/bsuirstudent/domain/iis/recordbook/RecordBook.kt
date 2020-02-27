package men.brakh.bsuirstudent.domain.iis.recordbook

import men.brakh.bsuirstudent.domain.BaseEntity
import java.util.*
import javax.persistence.*

@Entity(name = "record_book_subject_statistic")
data class SubjectStatistic(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "mark_id", referencedColumnName = "id")
    val mark: RecordBookMark,

    val averageMark: Double?,
    val retakeProbability: Double?
) : BaseEntity<Int>


@Entity(name = "record_book_mark")
data class RecordBookMark(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    val semester: RecordBookSemester,

    val subject: String,
    val formOfControl: String,
    val hours: Int?,
    val mark: String?,
    val date: Date?,
    val teacher: String?,

    val retakesCount: Int
) : BaseEntity<Int> {
    @OneToOne(
        mappedBy = "mark",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    lateinit var statistic: SubjectStatistic
}

@Entity(name = "record_book_semester")
data class RecordBookSemester(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "record_book_id", referencedColumnName = "id")
    val recordBook: RecordBook,

    val number: Int,
    val averageMark: Double

) : BaseEntity<Int> {
    @OneToMany(
        mappedBy = "semester",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    lateinit var marks: List<RecordBookMark>
}


@Entity(name = "record_book_diploma")
data class DiplomaInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "record_book_id", referencedColumnName = "id")
    val recordBook: RecordBook,

    val topic: String?,
    val teacher: String?
): BaseEntity<Int>

@Entity(name = "record_book")
data class RecordBook(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val number: String,
    val averageMark: Double
) : BaseEntity<Int> {
    @OneToMany(
        mappedBy = "recordBook",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    lateinit var semesters: List<RecordBookSemester>

    @OneToOne(
        mappedBy = "recordBook",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    lateinit var  diploma: DiplomaInfo
}
