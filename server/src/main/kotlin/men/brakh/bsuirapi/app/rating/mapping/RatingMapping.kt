package men.brakh.bsuirapi.app.rating.mapping

import men.brakh.bsuirapi.inrfastructure.bsuirapi.RatingDto
import men.brakh.bsuirapicore.model.data.RatingCheckPoint
import men.brakh.bsuirapicore.model.data.RatingItem

fun RatingDto.toEntity(): RatingItem {
    return RatingItem(
            recordBookNumber = this.studentCardNumber,
            averageGrade = this.average ?: 0.0,
            missedHours = this.hours ?: 0,
            averageShift = this.averageShift ?: 0.0,
            checkPoint = listOf(
                    RatingCheckPoint(number = 1, averageGrade = this.fistAverage ?: 0.0, missedHours = this.firstHours ?: 0),
                    RatingCheckPoint(number = 2, averageGrade = this.secondAverage ?: 0.0, missedHours = this.secondHours ?: 0),
                    RatingCheckPoint(number = 3, averageGrade = this.thirdAverage ?: 0.0, missedHours = this.thirdHours ?: 0)
            )

    )
}