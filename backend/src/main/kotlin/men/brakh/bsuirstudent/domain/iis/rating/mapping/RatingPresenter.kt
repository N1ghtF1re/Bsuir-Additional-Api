package men.brakh.bsuirstudent.domain.iis.rating.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.rating.RatingCheckPointDto
import men.brakh.bsuirstudent.domain.iis.rating.RatingItem
import men.brakh.bsuirstudent.domain.iis.rating.RatingItemDto
import org.springframework.stereotype.Component

@Component
class RatingPresenter : EntityPresenter<RatingItem, RatingItemDto> {
    override fun mapToDto(entity: RatingItem, dtoClass: Class<out RatingItemDto>): RatingItemDto {
        return RatingItemDto(
            averageGrade = entity.averageGrade,
            recordBookNumber = entity.recordBookNumber,
            averageShift = entity.averageShift,
            checkPoint =  entity.checkPoint.map { RatingCheckPointDto(number = it.number, averageGrade = it.averageGrade, missedHours = it.missedHours) },
            missedHours = entity.missedHours
        )
    }

}