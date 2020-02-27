package men.brakh.bsuirstudent.domain.news.service;

import men.brakh.bsuirstudent.application.search.SearchRequest
import men.brakh.bsuirstudent.application.search.SearchResponse
import men.brakh.bsuirstudent.domain.news.CreateNewsRequest
import men.brakh.bsuirstudent.domain.news.NewsDto
import men.brakh.bsuirstudent.domain.news.ShortNewsDto

interface NewsService {
  fun create(request: CreateNewsRequest): NewsDto
  fun search(searchRequest: SearchRequest): SearchResponse<ShortNewsDto>
  fun get(id: Int): NewsDto
}
