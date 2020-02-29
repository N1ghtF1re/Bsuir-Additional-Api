package men.brakh.bsuirstudent.domain.iis.recordbook.service

import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.application.bsuir.BsuirStudentService
import men.brakh.bsuirstudent.domain.iis.recordbook.RecordBook
import men.brakh.bsuirstudent.domain.iis.recordbook.RecordBookDto
import men.brakh.bsuirstudent.domain.iis.recordbook.mapping.RecordBookBsuirMapping
import men.brakh.bsuirstudent.domain.iis.recordbook.repository.RecordBookRepository
import men.brakh.bsuirstudent.domain.iis.student.mapping.RecordBookPresenter
import men.brakh.bsuirstudent.security.authentication.credentials.getCurrentUserUsername
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RequestMapping(path = ["/api/v2/students/me/record-book"])
@RestController
open class RecordBookServiceImpl(
    private val repository: RecordBookRepository,
    private val presenter: RecordBookPresenter,
    private val bsuirMapping: RecordBookBsuirMapping,
    private val bsuirService: BsuirStudentService
) : RecordBookService {
    private val cachedGetTemplate: CachedGetTemplate<RecordBook, RecordBookDto, Int> = CachedGetTemplate(
        repository,
        presenter
    )

    @Transactional
    @GetMapping
    override fun getMyRecordBook(): RecordBookDto {
        val currentUserUsername = getCurrentUserUsername();
        return cachedGetTemplate.get(
            getDataFunc = {
                bsuirMapping.mapToRecordBook(
                    bsuirService.getMarkBook(),
                    bsuirService.getDiploma()
                )
            },
            existsFunc = {
                repository.existsByNumber(currentUserUsername)
            },
            findFunc = {
                repository.findOneByNumber(currentUserUsername)
            },
            dtoClass = RecordBookDto::class.java
        )
    }

}