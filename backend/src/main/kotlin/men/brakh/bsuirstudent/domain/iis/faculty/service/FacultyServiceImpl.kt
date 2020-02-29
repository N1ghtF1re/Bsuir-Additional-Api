package men.brakh.bsuirstudent.domain.iis.faculty.service

import men.brakh.bsuirstudent.application.bsuir.BsuirFacultyService
import men.brakh.bsuirstudent.application.template.CachedGetTemplate
import men.brakh.bsuirstudent.domain.iis.faculty.Faculty
import men.brakh.bsuirstudent.domain.iis.faculty.FacultyDto
import men.brakh.bsuirstudent.domain.iis.faculty.mapping.FacultyBsuirMapping
import men.brakh.bsuirstudent.domain.iis.faculty.mapping.FacultyPresenter
import men.brakh.bsuirstudent.domain.iis.faculty.repository.FacultyRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v2/faculties"])
open class FacultyServiceImpl(
    repository: FacultyRepository,
    presenter: FacultyPresenter,
    private val bsuirMapping: FacultyBsuirMapping,
    private val bsuirService: BsuirFacultyService
) : FacultyService {

    private val cachedGetTemplate: CachedGetTemplate<Faculty, FacultyDto, Int> = CachedGetTemplate(
        repository,
        presenter
    )

    @GetMapping
    @ResponseBody
    @Transactional
    override fun getFaculties(): List<FacultyDto> {
        return cachedGetTemplate.getAll(
            getDataFunc = { bsuirMapping.mapToFaculties(bsuirService.getFaculties(), bsuirService.getSpecialities()) },
            dtoClass = FacultyDto::class.java
        )
    }
}