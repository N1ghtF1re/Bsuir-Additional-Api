package men.brakh.newsparser.parser

import men.brakh.newsparser.model.News
import men.brakh.newsparser.model.NewsSource
import java.util.*

interface Parser {
    val source: NewsSource

    fun parse(lastUpdate: Date): List<News>
}