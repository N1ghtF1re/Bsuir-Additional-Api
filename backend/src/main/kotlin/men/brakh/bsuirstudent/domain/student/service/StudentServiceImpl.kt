package men.brakh.bsuirstudent.domain.student.service

import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.domain.bsuir.BsuirProfileService
import men.brakh.bsuirstudent.domain.student.Student
import men.brakh.bsuirstudent.domain.student.StudentDto
import men.brakh.bsuirstudent.domain.student.mapping.StudentBsuirMapping
import men.brakh.bsuirstudent.domain.student.mapping.StudentPresenter
import men.brakh.bsuirstudent.domain.student.repository.StudentRepository
import men.brakh.bsuirstudent.security.authentication.credentials.getCurrentUserUsername
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RestController
@RequestMapping(path = ["/api/v2/students"])
open class StudentServiceImpl(
    private val bsuirService : BsuirProfileService,
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
    override fun getMe() : StudentDto {
        val currentUserUsername = getCurrentUserUsername();
        return cachedGetTemplate.get(
            getDataFunc = { studentBsuirMapping.mapPersonalCvToStudent(bsuirService.getPersonalCV()) },
            existsFunc = { repository.existsByRecordBookNumber(currentUserUsername) },
            findFunc = { repository.findOneByRecordBookNumber(currentUserUsername) },
            dtoClass = StudentDto::class.java
        )
    }
}