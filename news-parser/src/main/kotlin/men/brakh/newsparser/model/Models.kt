package men.brakh.newsparser.model

import java.util.*

data class NewsSource(val id: Long = -1, val type: String, val name: String)
data class News(val id: Long = -1, val title: String, val source: NewsSource, val content: String,
                val publishedAt: Long, val loadedAt: Long, val url: String, val urlToImage: String? = null)
