package men.brakh.newsparser.model.parser.site


import men.brakh.newsparser.model.dto.CreateNewsRequest
import men.brakh.newsparser.model.dto.NewsSourceDto
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

class FksisSiteParser() : SiteParser() {
    override val source = NewsSourceDto(type = "ФКСиС", name = "Портал ФКСиС", alias = "FKSIS_PORTAL")

    private val host = "http://fksis.bsuir.by"
    private val newsUrl = "$host/p/allnews"
    private val announcesUrl = "$host/p/allanns"


    private fun parseAnnounces(): Elements {
        val page = getDom(announcesUrl)

        return page.getElementsByClass("b-text-news-list")

    }

    private fun parseNews(): Elements {
        val page = getDom(newsUrl)

        return page.getElementsByClass("b-text-news-list")
    }

    private fun String.toDate(): Date {
        return SimpleDateFormat("dd.MM.yyyy").parse(this)
    }

    override fun parse(lastUpdate: Date): List<CreateNewsRequest> {
        return (parseAnnounces().asSequence() + parseNews().asSequence())
                .filter { htmlEl ->
                    val dateStr = htmlEl.getElementsByTag("small").first().text()
                    val date = dateStr.toDate()

                    date > lastUpdate
                }
                .map { htmlEl ->
                    val fullLink: String = host + htmlEl // Link to full page
                            .getElementsByClass("more")
                            .first()
                            .getElementsByTag("a")
                            .first()
                            .attr("href")

                    val fullNewsEl = getDom(fullLink)
                            .getElementsByClass("b-text-news")
                            .first()

                    val news: CreateNewsRequest = with(fullNewsEl) {
                        val date: Date = getElementsByTag("small")
                                .first()
                                .text()
                                .toDate()
                        val title: String = getElementsByTag("big")
                                .first()
                                .text()
                        val img: String? = getElementsByTag("img")
                                ?.first()
                                ?.attr("src")
                                ?.let { if(it.startsWith("/")) { "$host$it" } else {it}  }
                        val content = getElementsByClass("description")
                                .first()
                                .html()
                                .replace("src=\"/", "src=\"$host/")
                                .replace( "href=\"/", "href=\"$host/")
                                .toMd()

                        CreateNewsRequest(title = title,  urlToImage = img, content = content, url = fullLink, publishedAt = date,
                                loadedAt = Date(), sourceName = source.name, sourceType = source.type, sourceAlias = source.alias)
                    }
                    news
                }.sortedBy { it.publishedAt }
                 .toList()
    }
}

