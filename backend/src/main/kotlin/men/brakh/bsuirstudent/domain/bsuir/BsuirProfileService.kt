package men.brakh.bsuirstudent.domain.bsuir

import org.springframework.stereotype.Component

@Component
open class BsuirProfileService (
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    fun getPersonalCV(): PersonalCVBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/personalCV")

    fun getMarkBook(): MarkBookBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook")

    fun getDiploma(): DiplomaBsuirDto
            = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook/diploma")
}