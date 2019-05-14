package men.brakh.newsparser.model

import java.util.*

data class NewsSource(val type: String, val name: String)

data class News(val title: String, val source: NewsSource, val content: String,
                val publishedAt: Date, val loadedAt: Date, val url: String, val urlToImage: String? = null)

