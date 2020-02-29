package men.brakh.newsparser.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class CreateNewsRequest (
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
)

class NewsSourceDto (
        val name: String,
        val type: String,
        val alias: String
)