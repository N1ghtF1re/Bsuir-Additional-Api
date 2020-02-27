package men.brakh.bsuirstudent.domain.news.service;

import men.brakh.bsuirstudent.domain.news.NewsSourceDto

interface NewsSourceService {
    fun getSources(): List<NewsSourceDto>
}
