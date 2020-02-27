package men.brakh.bsuirstudent.domain.news

import men.brakh.bsuirstudent.domain.BaseEntity
import java.util.*
import javax.persistence.*

@Entity(name = "news_source")
data class NewsSource(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val alias: String,
    val type: String,
    val name: String
) : BaseEntity<Int>

@Entity(name = "news")
data class News(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val title: String,

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    val source: NewsSource,

    val content: String,

    val publishedAt: Date,

    val loadedAt: Date,

    val url: String,

    val urlToImage: String? = null,

    val shortContent: String
) : BaseEntity<Int>