package men.brakh.bsuirstudent.domain.news

import men.brakh.bsuirstudent.application.search.SearchRequest
import men.brakh.bsuirstudent.application.search.SearchResponse
import men.brakh.bsuirstudent.application.template.CreateTemplate
import men.brakh.bsuirstudent.application.template.GetTemplate
import men.brakh.bsuirstudent.application.template.SearchTemplate
import men.brakh.bsuirstudent.domain.news.mapping.NewsMapper
import men.brakh.bsuirstudent.domain.news.mapping.NewsPresenter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v2/news"])
class NewsService(
    newsPresenter: NewsPresenter,
    newsMapper: NewsMapper,
    newsRepository: NewsRepository
) {
    private val createTemplate = CreateTemplate(
        newsRepository,
        newsMapper,
        newsPresenter
    )

    private val getTemplate = GetTemplate(newsPresenter, newsRepository)

    private val searchTemplate = SearchTemplate(newsPresenter, newsRepository)

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody request: CreateNewsRequest): NewsDto {
        return createTemplate.save(request, NewsDto::class.java) as NewsDto
    }

    @ResponseBody
    @PostMapping("/search")
    fun search(@RequestBody searchRequest: SearchRequest): SearchResponse<ShortNewsDto> {
        return searchTemplate.search(searchRequest, ShortNewsDto::class.java)
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun get(@PathVariable(name = "id") id: Int): NewsDto {
        return getTemplate.getById(id, NewsDto::class.java) as NewsDto
    }

}