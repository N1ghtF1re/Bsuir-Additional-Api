package men.brakh.bsuirstudent.application.bsuir

import men.brakh.bsuirstudent.application.exception.IisException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import org.springframework.stereotype.Component

@Component
open class BsuirStudentService (
    private val bsuirApiExecutor: BsuirApiExecutor
) {
    fun getPersonalCV(): PersonalCVBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/personalCV")!!

    fun getMarkBook(): MarkBookBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook")!!

    fun getDiploma(): DiplomaBsuirDto  {
        return try {
            bsuirApiExecutor.makeAuthorizedGetRequest("/portal/markbook/diploma")!!
        } catch (e: Exception) {
            DiplomaBsuirDto(name = null, theme = null)
        }
    }

    @Throws(exceptionClasses = [ResourceNotFoundException::class])
    fun getUserCV(id: Int): PersonalCVBsuirDto {
        return try {
            bsuirApiExecutor.makeAuthorizedGetRequest("/profiles?id=$id")!!
        } catch (e: IisException) {
            throw ResourceNotFoundException("User with iis id $id is not found")
        }
    }

    fun getGroup(): GroupInfoBsuirDto
        = bsuirApiExecutor.makeAuthorizedGetRequest("/portal/groupInfo")!!

    fun saveShowRating(isShow: Boolean)
        = bsuirApiExecutor.makeAuthorizedPutRequest<Void>("/portal/saveShowRating", mapOf("showRating" to isShow))

    fun savePublished(isPublished: Boolean)
            = bsuirApiExecutor.makeAuthorizedPutRequest<Void>("/portal/savePublished", mapOf("published" to isPublished))

    fun saveSearchJob(isSearchJob: Boolean)
            = bsuirApiExecutor.makeAuthorizedPutRequest<Void>("/portal/saveSearchJob", mapOf("searchJob" to isSearchJob))


}