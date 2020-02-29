package men.brakh.bsuirstudent.application.bsuir

import org.springframework.stereotype.Component

@Component
class BsuirRatingService(
    private val bsuirApiExecutor: BsuirApiExecutor
) {

    fun getRating(year: Int, specialityId: Int): List<RatingBsuirDto>
        = bsuirApiExecutor.makeUnauthorizedGetRequest<Array<RatingBsuirDto>>(
            "/portal/allRating?year=$year&sdef=$specialityId"
        )!!.toList()

}