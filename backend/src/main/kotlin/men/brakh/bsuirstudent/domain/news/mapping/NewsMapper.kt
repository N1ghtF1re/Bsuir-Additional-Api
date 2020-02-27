package men.brakh.bsuirstudent.domain.news.mapping

import men.brakh.bsuirstudent.application.mapping.mapper.CreateDtoMapper
import men.brakh.bsuirstudent.domain.news.CreateNewsRequest
import men.brakh.bsuirstudent.domain.news.News
import men.brakh.bsuirstudent.domain.news.NewsSource
import men.brakh.bsuirstudent.domain.news.repository.NewsSourceRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class NewsMapper(
    private val newsSourceRepository: NewsSourceRepository
) : CreateDtoMapper<CreateNewsRequest, News> {

    private val urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"
    override fun mapToEntity(createRequest: CreateNewsRequest): News {
        val newsSource = getSource(createRequest)
        return News(
            title = createRequest.title,
            content = createRequest.content,
            loadedAt = Date(),
            publishedAt = createRequest.publishedAt,
            shortContent = truncateContent(createRequest.content),
            source = newsSource,
            url = createRequest.url,
            urlToImage = createRequest.urlToImage
        )
    }

    private fun truncateContent(content: String): String {
        val withoutImages = content
            .replace("!\\[[^\\]]+\\]\\([^\\)]+\\)".toRegex(), "")
            .replace("\\[[^\\]]+\\]: $urlRegex".toRegex(), "")

        return if (withoutImages.length < 255) {
            withoutImages
        } else {
            withoutImages.substring(0..255) + "..."
        }
    }

    private fun getSource(dto: CreateNewsRequest): NewsSource {
        return newsSourceRepository.findOneByAlias(dto.sourceAlias)
            ?: newsSourceRepository.save(
                NewsSource(
                    name = dto.sourceName,
                    type = dto.sourceType,
                    alias = dto.sourceAlias
                )
            )
    }

}