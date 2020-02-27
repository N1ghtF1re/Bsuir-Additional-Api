package men.brakh.bsuirstudent.domain.iis.student.service

import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.domain.iis.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.domain.iis.student.StudentDto
import men.brakh.bsuirstudent.domain.iis.student.mapping.StudentBsuirMapping
import men.brakh.bsuirstudent.domain.iis.student.mapping.StudentPresenter
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.security.authentication.credentials.getCurrentUserUsername
import org.springframework.web.bind.annotation.*
import javax.transaction.Transactional

@RestController
@RequestMapping(path = ["/api/v2/students"])
open class StudentServiceImpl(
    private val bsuirService : BsuirStudentService,
    private val studentBsuirMapping: StudentBsuirMapping,
    private val presenter: StudentPresenter,
    private val repository: StudentRepository
) : StudentService {
    private val cachedGetTemplate: CachedGetTemplate<Student, StudentDto, Int> = CachedGetTemplate(
        repository,
        presenter
    )

    @GetMapping("/me")
    @Transactional
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
}