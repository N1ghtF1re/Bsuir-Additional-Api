package men.brakh.bsuirstudent.domain.iis.rating

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import men.brakh.bsuirstudent.application.serializer.DoubleContextualSerializer
import men.brakh.bsuirstudent.domain.Dto

data class RatingCheckPointDto(
    val number: Int,
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageGrade: Double,
    val missedHours: Int
) : Dto


data class RatingItemDto(
    val recordBookNumber: String,
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageGrade: Double,
    val missedHours: Int,
    @JsonSerialize(using = DoubleContextualSerializer::class)
    val averageShift: Double,
    val checkPoint: List<RatingCheckPointDto>
) : Dto