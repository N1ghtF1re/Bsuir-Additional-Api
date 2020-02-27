package men.brakh.bsuirstudent.domain.iis.group.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.group.Group
import men.brakh.bsuirstudent.domain.iis.group.GroupDto
import men.brakh.bsuirstudent.domain.iis.group.GroupMemberDto
import org.springframework.stereotype.Component

@Component
class GroupPresenter : EntityPresenter<Group, GroupDto> {
    override fun mapToDto(entity: Group, dtoClass: Class<out GroupDto>): GroupDto {
        return GroupDto(
            number = entity.number,
            members = entity.members.map {
                GroupMemberDto(
                    role = it.role,
                    phone = it.phone,
                    email = it.email,
                    name = it.name
                )
            }
        )
    }

}