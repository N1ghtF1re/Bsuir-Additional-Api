package men.brakh.bsuirstudent.domain.iis.group

import men.brakh.bsuirstudent.domain.Dto


data class GroupMemberDto (
    val role: String?,
    val name: String,
    val email: String?,
    val phone: String?
): Dto

data class GroupDto (
    val number: String,
    val members: List<GroupMemberDto>
): Dto