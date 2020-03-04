package men.brakh.bsuirstudent.domain.news.subscription

import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.domain.news.NewsSource
import men.brakh.bsuirstudent.notification.subscription.Subscription
import javax.persistence.*

@Entity(name = "news_source_subscription")
data class NewsSourceSubscription(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    override val user: Student,

    @ManyToOne
    @JoinColumn(name = "news_source_id", referencedColumnName = "id")
    override val entity: NewsSource
) : Subscription<NewsSource, Int>