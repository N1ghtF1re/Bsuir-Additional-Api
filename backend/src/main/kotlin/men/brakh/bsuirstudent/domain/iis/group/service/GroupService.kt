package men.brakh.bsuirstudent.domain.iis.group.service

import men.brakh.bsuirstudent.domain.iis.group.GroupDto

interface GroupService {
    fun getMyGroup(): GroupDto
}