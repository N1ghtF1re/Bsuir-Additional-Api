package men.brakh.bsuirstudent.domain.news.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.news.NewsSource
import men.brakh.bsuirstudent.domain.news.NewsSourceDto
import org.springframework.stereotype.Component

@Component
class NewsSourcePresenter : EntityPresenter<NewsSource, NewsSourceDto> {
    override fun mapToDto(entity: NewsSource, dtoClass: Class<out NewsSourceDto>): NewsSourceDto {
        return NewsSourceDto(
            id = entity.id ?: -1,
            sourceName = entity.name,
            sourceType = entity.type,
            sourceAlias = entity.alias
        )
    }

}