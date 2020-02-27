package men.brakh.bsuirstudent.domain.iis.bsuir

import men.brakh.bsuirstudent.application.exception.IisException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import org.springframework.stereotype.Component

@Component
open class BsuirStudentService (
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    fun getPersonalCV(): PersonalCVBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/personalCV")

    fun getMarkBook(): MarkBookBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook")

    fun getDiploma(): DiplomaBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook/diploma")

    fun getUserCV(id: Int): PersonalCVBsuirDto {
        return try {
            bsuirApiExecutor.makeAuthorizedGetRequest("/profiles?id=$id")
        } catch (e: IisException) {
            throw ResourceNotFoundException("User with iis id $id is not found")
        }
    }

    fun getGroup(): GroupInfoBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/groupInfo")

}