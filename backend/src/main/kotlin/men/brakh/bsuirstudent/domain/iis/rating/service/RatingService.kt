package men.brakh.bsuirstudent.domain.iis.rating.service

import men.brakh.bsuirstudent.domain.iis.rating.RatingItemDto

interface RatingService {
    fun getRating(year: Int, specialityId: Int): List<RatingItemDto>
}