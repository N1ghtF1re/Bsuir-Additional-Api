package men.brakh.bsuirstudent.domain.recordbook

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import men.brakh.bsuirstudent.application.serializer.DoubleContextualSerializer
import men.brakh.bsuirstudent.domain.Dto
import java.util.*

data class SubjectStatisticDto(
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageMark: Double,
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val retakeProbability: Double
): Dto


data class RecordBookMarkDto(
    val subject: String,
    val formOfControl: String,
    val hours: Int?,
    val mark: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: Date?,
    val teacher: String?,

    val retakesCount: Int,

    val statistic: SubjectStatisticDto?
) : Dto

data class RecordBookSemesterDto(
    val number: Int,
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageMark: Double,
    val marks: List<RecordBookMarkDto>

) : Dto


data class DiplomaInfoDto(
    val topic: String,
    val teacher: String
): Dto

data class RecordBookDto(
    val number: String,

    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageMark: Double,

    val semesters: List<RecordBookSemesterDto>,
    val diploma: DiplomaInfoDto?

) : Dto