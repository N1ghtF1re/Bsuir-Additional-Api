package men.brakh.newsparser.model.parser

import men.brakh.bsuirapicore.model.data.News
import men.brakh.bsuirapicore.model.data.NewsSource
import java.util.*

interface Parser {
    val source: NewsSource

    fun parse(lastUpdate: Date): List<News>
}