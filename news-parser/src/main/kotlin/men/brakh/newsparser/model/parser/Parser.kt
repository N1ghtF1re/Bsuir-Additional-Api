package men.brakh.newsparser.model.parser

import men.brakh.newsparser.model.dto.CreateNewsRequest
import men.brakh.newsparser.model.dto.NewsSourceDto
import java.util.*

interface Parser {
    val source: NewsSourceDto

    fun parse(lastUpdate: Date): List<CreateNewsRequest>
}