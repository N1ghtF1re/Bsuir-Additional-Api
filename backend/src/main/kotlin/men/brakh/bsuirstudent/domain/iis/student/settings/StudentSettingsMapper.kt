package men.brakh.bsuirstudent.domain.iis.student.settings

import men.brakh.bsuirstudent.application.mapping.mapper.UpdateDtoMapper
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.student.UpdateUserSettingsRequest
import men.brakh.bsuirstudent.domain.iis.student.UserSettings
import men.brakh.bsuirstudent.domain.iis.student.UserSettingsDto
import org.springframework.stereotype.Component

@Component
class StudentSettingsMapper : UpdateDtoMapper<UpdateUserSettingsRequest, UserSettings> {
    override fun mapToEntity(entity: UserSettings, updateRequest: UpdateUserSettingsRequest): UserSettings {
        return UserSettings(
            isPublicProfile = updateRequest.isPublicProfile,
            isSearchJob = updateRequest.isSearchJob,
            isShowRating = updateRequest.isShowRating,
            student = entity.student,
            id = entity.id
        )
    }

}

@Component
class StudentSettingsPresenter : EntityPresenter<UserSettings, UserSettingsDto> {
    override fun mapToDto(entity: UserSettings, dtoClass: Class<out UserSettingsDto>): UserSettingsDto {
        return UserSettingsDto(
            isShowRating = entity.isShowRating,
            isSearchJob = entity.isSearchJob,
            isPublicProfile = entity.isPublicProfile
        )
    }

}