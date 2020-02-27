package men.brakh.bsuirstudent.domain.iis.student.service

import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.domain.iis.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.domain.iis.student.StudentDto
import men.brakh.bsuirstudent.domain.iis.student.UpdateUserSettingsRequest
import men.brakh.bsuirstudent.domain.iis.student.UserSettingsDto
import men.brakh.bsuirstudent.domain.iis.student.mapping.StudentBsuirMapping
import men.brakh.bsuirstudent.domain.iis.student.mapping.StudentPresenter
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.domain.iis.student.settings.SettingsUpdateTemplate
import men.brakh.bsuirstudent.security.authentication.credentials.getCurrentUserUsername
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v2/students"])
open class StudentServiceImpl(
    private val bsuirService : BsuirStudentService,
    private val studentBsuirMapping: StudentBsuirMapping,
    private val presenter: StudentPresenter,
    private val repository: StudentRepository,
    private val userSettingsUpdateTemplate: SettingsUpdateTemplate
) : StudentService {
    private val cachedGetTemplate: CachedGetTemplate<Student, StudentDto, Int> = CachedGetTemplate(
        repository,
        presenter
    )

    @GetMapping("/me")
    @Transactional(rollbackFor = [Exception::class])
    @ResponseBody
    override fun getMe() : StudentDto {
        val currentUserUsername = getCurrentUserUsername();
        return cachedGetTemplate.get(
            getDataFunc = { studentBsuirMapping.mapPersonalCvToStudent(bsuirService.getPersonalCV()) },
            existsFunc = { repository.existsByRecordBookNumber(currentUserUsername) },
            findFunc = { repository.findOneByRecordBookNumber(currentUserUsername) },
            dtoClass = StudentDto::class.java
        )
    }

    @GetMapping("/{iisId}")
    @ResponseBody
    override fun getStudent(@PathVariable(name = "iisId") iisId: Int): StudentDto {
        val bsuirDto = bsuirService.getUserCV(iisId)
        val student = studentBsuirMapping.mapPersonalCvToStudent(bsuirDto)
        return presenter.mapToDto(student, StudentDto::class.java)
    }

    @GetMapping("/me/settings")
    @ResponseBody
    @Transactional(rollbackFor = [Exception::class])
    override fun getSettings(): UserSettingsDto {
        return getMe().settings
    }

    @PutMapping("/me/settings")
    @ResponseBody
    @Transactional(rollbackFor = [Exception::class])
    override fun updateSettings(@RequestBody request: UpdateUserSettingsRequest): UserSettingsDto {
        val currentUserUsername = getCurrentUserUsername();
        val me = repository.findOneByRecordBookNumber(currentUserUsername)!!

        bsuirService.savePublished(request.isPublicProfile)
        bsuirService.saveSearchJob(request.isSearchJob)
        bsuirService.saveShowRating(request.isShowRating)

        return userSettingsUpdateTemplate.update(me.settings!!.id!!, request, UserSettingsDto::class.java)
    }
}