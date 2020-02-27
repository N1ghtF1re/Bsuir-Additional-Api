package men.brakh.bsuirstudent.domain.iis.student.settings

import men.brakh.bsuirstudent.application.mapping.mapper.UpdateDtoMapper
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.application.template.UpdateTemplate
import men.brakh.bsuirstudent.domain.iis.student.UpdateUserSettingsRequest
import men.brakh.bsuirstudent.domain.iis.student.UserSettings
import men.brakh.bsuirstudent.domain.iis.student.UserSettingsDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
class SettingsUpdateTemplate(
    repository: JpaRepository<UserSettings, Int>,
    dtoMapper: UpdateDtoMapper<UpdateUserSettingsRequest, UserSettings>,
    entityPresenter: EntityPresenter<UserSettings, UserSettingsDto>
) : UpdateTemplate<UserSettings, UpdateUserSettingsRequest, UserSettingsDto, Int>(
    repository,
    dtoMapper,
    entityPresenter
)