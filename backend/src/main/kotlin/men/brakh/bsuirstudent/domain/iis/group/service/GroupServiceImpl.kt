package men.brakh.bsuirstudent.domain.iis.group.service

import men.brakh.bsuirstudent.application.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.domain.iis.group.Group
import men.brakh.bsuirstudent.domain.iis.group.GroupDto
import men.brakh.bsuirstudent.domain.iis.group.mapping.GroupBsuirMapping
import men.brakh.bsuirstudent.domain.iis.group.mapping.GroupPresenter
import men.brakh.bsuirstudent.domain.iis.group.repository.GroupRepository
import men.brakh.bsuirstudent.domain.iis.student.service.StudentService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path=["/api/v2/students/me/group"])
open class GroupServiceImpl(
    private val repository: GroupRepository,
    private val presenter: GroupPresenter,
    private val bsuirMapping: GroupBsuirMapping,
    private val bsuirService: BsuirStudentService,
    private val studentService: StudentService
) : GroupService {
    private val cachedGetTemplate: CachedGetTemplate<Group, GroupDto, Int> = CachedGetTemplate(
        repository,
        presenter
    )

    @GetMapping
    @ResponseBody
    @Transactional
    override fun getMyGroup(): GroupDto {
        val me = studentService.getMe()

        return cachedGetTemplate.get(
            getDataFunc = { bsuirMapping.mapToGroup(bsuirService.getGroup()) },
            existsFunc = { repository.existsByNumber(me.educationInfo.group) },
            findFunc = { repository.findOneByNumber(me.educationInfo.group) },
            dtoClass = GroupDto::class.java
        )
    }
}