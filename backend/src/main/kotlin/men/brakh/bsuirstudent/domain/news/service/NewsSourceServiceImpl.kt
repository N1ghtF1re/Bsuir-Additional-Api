package men.brakh.bsuirstudent.domain.news.service

import men.brakh.bsuirstudent.application.template.GetTemplate
import men.brakh.bsuirstudent.domain.news.NewsSourceDto
import men.brakh.bsuirstudent.domain.news.mapping.NewsSourcePresenter
import men.brakh.bsuirstudent.domain.news.repository.NewsSourceRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v2/news/sources"])
class NewsSourceServiceImpl(
    repository: NewsSourceRepository,
    presenter: NewsSourcePresenter
): NewsSourceService {
    private val getTemplate = GetTemplate(presenter, repository)

    @GetMapping
    override fun getSources(): List<NewsSourceDto> {
        return getTemplate.getAll(NewsSourceDto::class.java)
    }
}