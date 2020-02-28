package men.brakh.bsuirstudent.domain.iis.group.mapping

import men.brakh.bsuirstudent.application.extensions.getNotEmpty
import men.brakh.bsuirstudent.domain.iis.bsuir.GroupInfoBsuirDto
import men.brakh.bsuirstudent.domain.iis.group.Group
import men.brakh.bsuirstudent.domain.iis.group.GroupMember
import org.springframework.stereotype.Component

@Component
class GroupBsuirMapping {
    fun mapToGroup(groupInfoBsuirDto: GroupInfoBsuirDto, id: Int? = null): Group {
        val group = Group(
            id = id,
            number = groupInfoBsuirDto.numberGroup
        )

        group.members = groupInfoBsuirDto.groupInfoStudentDto.map {
            GroupMember(
                group = group,
                name = it.fio,
                email = it.email.getNotEmpty(),
                phone = it.phone.getNotEmpty(),
                role = it.position.getNotEmpty()
            )
        }
        return group
    }
}