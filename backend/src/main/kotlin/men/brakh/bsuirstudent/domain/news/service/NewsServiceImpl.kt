package men.brakh.bsuirstudent.domain.news.service

import men.brakh.bsuirstudent.application.search.SearchRequest
import men.brakh.bsuirstudent.application.search.SearchResponse
import men.brakh.bsuirstudent.application.search.Sort
import men.brakh.bsuirstudent.application.search.SortType
import men.brakh.bsuirstudent.application.template.CreateTemplate
import men.brakh.bsuirstudent.application.template.GetTemplate
import men.brakh.bsuirstudent.application.template.SearchTemplate
import men.brakh.bsuirstudent.domain.news.CreateNewsRequest
import men.brakh.bsuirstudent.domain.news.News
import men.brakh.bsuirstudent.domain.news.NewsDto
import men.brakh.bsuirstudent.domain.news.ShortNewsDto
import men.brakh.bsuirstudent.domain.news.mapping.NewsMapper
import men.brakh.bsuirstudent.domain.news.mapping.NewsPresenter
import men.brakh.bsuirstudent.domain.news.repository.NewsRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v2/news"])
open class NewsServiceImpl(
    newsPresenter: NewsPresenter,
    newsMapper: NewsMapper,
    newsRepository: NewsRepository,
    eventPublisher: ApplicationEventPublisher
): NewsService {
    private val createTemplate: CreateTemplate<News, CreateNewsRequest, ShortNewsDto> = CreateTemplate(
        newsRepository,
        newsMapper,
        newsPresenter,
        eventPublisher = eventPublisher
    )
    private val getTemplate: GetTemplate<News, ShortNewsDto, Int> = GetTemplate(newsPresenter, newsRepository)
    private val searchTemplate: SearchTemplate<News, ShortNewsDto> = SearchTemplate(newsPresenter, newsRepository)

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PreAuthorize("hasRole('ROLE_NEWS_CREATOR')")
    @PostMapping
    override fun create(@RequestBody request: CreateNewsRequest): NewsDto {
        return createTemplate.save(request, NewsDto::class.java) as NewsDto
    }

    @ResponseBody
    @Transactional(readOnly = true)
    @PostMapping("/search")
    override fun search(@RequestBody searchRequest: SearchRequest): SearchResponse<ShortNewsDto> {
        val sortedSearchRequest = if (searchRequest.sortBy == null)
            searchRequest.copy(sortBy = Sort(field = "publishedAt", type = SortType.DESC))
        else
            searchRequest

        return searchTemplate.search(sortedSearchRequest, ShortNewsDto::class.java)
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @ResponseBody
    override fun get(@PathVariable(name = "id") id: Int): NewsDto {
        return getTemplate.getById(id, NewsDto::class.java) as NewsDto
    }



}