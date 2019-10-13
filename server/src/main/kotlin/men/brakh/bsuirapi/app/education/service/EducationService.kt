package men.brakh.bsuirapi.app.education.service

import men.brakh.bsuirapicore.model.data.DiplomaInfo
import men.brakh.bsuirapicore.model.data.GroupInfo
import men.brakh.bsuirapicore.model.data.RecordBook

interface EducationService {
    fun getCurrentUserDimploma(): DiplomaInfo
    fun getCurrentUserGroupInfo(): GroupInfo
    fun getCurrentUserRecordBook(): RecordBook
}