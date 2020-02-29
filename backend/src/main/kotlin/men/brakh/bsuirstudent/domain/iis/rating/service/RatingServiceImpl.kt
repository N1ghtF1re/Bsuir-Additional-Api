package men.brakh.bsuirstudent.domain.iis.rating.service

import men.brakh.bsuirstudent.application.bsuir.BsuirRatingService
import men.brakh.bsuirstudent.domain.iis.rating.RatingItemDto
import men.brakh.bsuirstudent.domain.iis.rating.mapping.RatingBsuirMapping
import men.brakh.bsuirstudent.domain.iis.rating.mapping.RatingPresenter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v2/rating"])
class RatingServiceImpl(
    private val bsuirService: BsuirRatingService,
    private val bsuirMapping: RatingBsuirMapping,
    private val ratingPresenter: RatingPresenter
) : RatingService {
    @ResponseBody
    @GetMapping
    override fun getRating(
        @RequestParam(required = true) year: Int,
        @RequestParam(required = true) specialityId: Int
    ): List<RatingItemDto> {
        return ratingPresenter.mapListToDto(
            bsuirService.getRating(year, specialityId).map { bsuirMapping.mapToRatingItem(it) },
            RatingItemDto::class.java
        )
    }
}