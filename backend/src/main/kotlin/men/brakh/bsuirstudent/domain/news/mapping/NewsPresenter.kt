package men.brakh.bsuirstudent.domain.news.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.news.News
import men.brakh.bsuirstudent.domain.news.NewsDto
import men.brakh.bsuirstudent.domain.news.NewsSourceDto
import men.brakh.bsuirstudent.domain.news.ShortNewsDto
import org.springframework.stereotype.Component

@Component
class NewsPresenter(
    private val newsSourcePresenter: NewsSourcePresenter
) : EntityPresenter<News, ShortNewsDto> {
    override fun mapToDto(entity: News, dtoClass: Class<out ShortNewsDto>): ShortNewsDto {
        if (dtoClass == ShortNewsDto::class.java) {
            return ShortNewsDto(
                title = entity.title,
                urlToImage = entity.urlToImage,
                url = entity.url,
                shortContent = entity.shortContent,
                publishedAt = entity.publishedAt,
                loadedAt = entity.loadedAt,
                source = newsSourcePresenter.mapToDto(entity = entity.source, dtoClass = NewsSourceDto::class.java),
                id = entity.id ?: -1
            )
        }

        if (dtoClass == NewsDto::class.java) {
            return  NewsDto(
                title = entity.title,
                urlToImage = entity.urlToImage,
                url = entity.url,
                shortContent = entity.shortContent,
                publishedAt = entity.publishedAt,
                loadedAt = entity.loadedAt,
                source = newsSourcePresenter.mapToDto(entity = entity.source, dtoClass = NewsSourceDto::class.java),
                id = entity.id ?: -1,
                content = entity.content
            )
        }

        throw IllegalStateException()
    }
}