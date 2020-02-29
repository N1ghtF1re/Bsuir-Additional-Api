package men.brakh.bsuirstudent.domain.iis.rating

import men.brakh.bsuirstudent.domain.BaseEntity

data class RatingCheckPoint(
    override var id: Int? = null,

    val number: Int,
    val averageGrade: Double,
    val missedHours: Int
) : BaseEntity<Int>


data class RatingItem(
    override var id: Int? = null,
    val recordBookNumber: String,
    val averageGrade: Double,
    val missedHours: Int,
    val averageShift: Double,
    val checkPoint: List<RatingCheckPoint>
) : BaseEntity<Int>