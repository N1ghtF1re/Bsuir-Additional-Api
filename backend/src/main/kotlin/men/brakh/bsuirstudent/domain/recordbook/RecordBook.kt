package men.brakh.bsuirstudent.domain.recordbook

import men.brakh.bsuirstudent.domain.BaseEntity
import java.util.*
import javax.persistence.*
/*
@Entity(name = "record_book_subject_statistic")
data class SubjectStatistic(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val averageMark: Double?,
    val retakeProbability: Double?
) : BaseEntity<Int>

@Entity(name = "record_book_mark")
data class RecordBookMark(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val subject: String,
    val formOfControl: String,
    val hours: Int?,
    val mark: String?,
    val date: Date?,
    val teacher: String?,
    val statistic: SubjectStatistic?,
    val retakesCount: Int
) : BaseEntity<Int>

@Entity(name = "record_book_semester")
data class RecordBookSemester(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "record_book_id", referencedColumnName = "id")
    val recordBook: RecordBook,

    val number: Int,
    val averageMark: Double,
    val marks: List<RecordBookMark>
) : BaseEntity<Int>


@Entity(name = "record_book")
data class RecordBook(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val number: String,
    val averageMark: Double,

    @OneToMany(
        mappedBy = "recordBook",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val semesters: List<RecordBookSemester>
) : BaseEntity<Int>

 */