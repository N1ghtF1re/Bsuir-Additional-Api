package men.brakh.bsuirapi.app.rating.service

import men.brakh.bsuirapi.app.rating.mapping.toEntity
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.RatingItem

class RatingService(val bsuirApi: BsuirApi) {
    fun getRating(year: Int, specialityId: Int): List<RatingItem> {
        return bsuirApi.getRating(year, specialityId).map { it.toEntity() }
    }
}