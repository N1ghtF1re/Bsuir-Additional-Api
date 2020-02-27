package men.brakh.bsuirstudent.domain.news

import com.fasterxml.jackson.annotation.JsonFormat
import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto
import java.util.*

class NewsSourceDto (
    val id: Int,
    val sourceName: String,
    val sourceType: String,
    val sourceAlias: String
) : Dto

open class ShortNewsDto (
    val id: Int,

    val title: String,

    val sourceName: String,
    val sourceType: String,
    val sourceAlias: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val publishedAt: Date,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val loadedAt: Date,

    val url: String,

    val urlToImage: String?,

    val shortContent: String
): Dto


open class NewsDto(
    id: Int,
    title: String,
    sourceName: String,
    sourceType: String,
    sourceAlias: String,
    publishedAt: Date,
    loadedAt: Date,
    url: String,
    urlToImage: String?,
    shortContent: String,
    var content: String
) : ShortNewsDto(
    id,
    title,
    sourceName,
    sourceType,
    sourceAlias,
    publishedAt,
    loadedAt,
    url,
    urlToImage,
    shortContent
)

open class CreateNewsRequest (
    val title: String,

    val sourceName: String,
    val sourceType: String,
    val sourceAlias: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val publishedAt: Date,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val loadedAt: Date,

    val url: String,

    val urlToImage: String?,

    val content: String
): Dto, CreateDto