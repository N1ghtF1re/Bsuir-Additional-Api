package men.brakh.bsuirstudent.domain.iis.rating.mapping

import men.brakh.bsuirstudent.application.bsuir.RatingBsuirDto
import men.brakh.bsuirstudent.domain.iis.rating.RatingCheckPoint
import men.brakh.bsuirstudent.domain.iis.rating.RatingItem
import org.springframework.stereotype.Component

@Component
class RatingBsuirMapping {
    fun mapToRatingItem(bsuirDto: RatingBsuirDto): RatingItem {
        return RatingItem(
            recordBookNumber = bsuirDto.studentCardNumber,
            averageGrade = bsuirDto.average ?: 0.0,
            missedHours = bsuirDto.hours ?: 0,
            averageShift = bsuirDto.averageShift ?: 0.0,
            checkPoint = listOf(
                RatingCheckPoint(number = 1, averageGrade = bsuirDto.fistAverage ?: 0.0, missedHours = bsuirDto.firstHours ?: 0),
                RatingCheckPoint(number = 2, averageGrade = bsuirDto.secondAverage ?: 0.0, missedHours = bsuirDto.secondHours ?: 0),
                RatingCheckPoint(number = 3, averageGrade = bsuirDto.thirdAverage ?: 0.0, missedHours = bsuirDto.thirdHours ?: 0)
            )

        )
    }
}