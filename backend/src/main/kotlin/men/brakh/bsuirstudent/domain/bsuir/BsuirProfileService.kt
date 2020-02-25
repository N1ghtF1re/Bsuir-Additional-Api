package men.brakh.bsuirstudent.domain.bsuir

import org.springframework.stereotype.Component

@Component
open class BsuirProfileService (
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    fun getPersonalCV(): PersonalCVDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/personalCV")
}