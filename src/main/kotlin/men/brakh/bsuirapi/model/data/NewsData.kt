package men.brakh.bsuirapi.model.data

import java.util.*


data class NewsSource(@Transient val id: Long = -1, val type: String, val name: String)

/**
 * @param publishedAt - Date of publication at the source
 * @param loadedAt - Date of loading at the server (Not always right after publication)
 */
data class News(@Transient val id: Long = -1, val title: String, val source: NewsSource, val content: String,
                val publishedAt: Date, val loadedAt: Date, val url: String, val urlToImage: String? = null)