package men.brakh.bsuirapicore.model.data

import java.util.*

data class SubjectStatistic(val averageMark: Double?, val averageRetakes: Int?)

data class Mark(val subject: String,
                val formOfControl: String,
                val hours: Int?,
                val mark: String?,
                val date: Date?,
                val teacher: String?,
                val statistic: SubjectStatistic?,
                val retakesCount: Int)

data class Semester(val number: Int, val averageMark: Double, val marks: List<Mark>)

data class RecordBook(val number: String, val averageMark: Double, val semesters: List<Semester>)

data class DiplomaInfo(val topic: String?, val teacher: String?)