package men.brakh.bsuirstudent.domain.news.subscription.serivce

import men.brakh.bsuirstudent.application.event.BaseEntityEvent
import men.brakh.bsuirstudent.application.event.EntityCreatedEvent
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.student.Student
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.domain.iis.student.service.StudentService
import men.brakh.bsuirstudent.domain.news.News
import men.brakh.bsuirstudent.domain.news.repository.NewsRepository
import men.brakh.bsuirstudent.domain.news.repository.NewsSourceRepository
import men.brakh.bsuirstudent.domain.news.subscription.NewsSourceSubscription
import men.brakh.bsuirstudent.domain.news.subscription.NewsSourceSubscriptionRequest
import men.brakh.bsuirstudent.domain.news.subscription.repository.NewsSourceSubscriptionRepository
import men.brakh.bsuirstudent.notification.sender.GlobalNotificationSender
import men.brakh.bsuirstudent.notification.subscription.SubscriptionServiceTemplate
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v2/news/sources/subscriptions"])
open class NewsSourceSubscriptionServiceImpl(
    private val studentService: StudentService,
    private val studentRepository: StudentRepository,
    private val newsSourceRepository: NewsSourceRepository,
    private val repository: NewsSourceSubscriptionRepository,
    private val newsRepository: NewsRepository,
    globalNotificationSender: GlobalNotificationSender
) : NewsSourceSubscriptionService, SubscriptionServiceTemplate(globalNotificationSender) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @GetMapping
    @Transactional(readOnly = true)
    override fun getSubscriptions(): List<String> {
        val me = studentService.getMe()

        return repository.findAllByUserId(me.id).map { it.entity.alias }
    }

    @PutMapping
    @Transactional
    override fun updateSubscriptions(@RequestBody request: NewsSourceSubscriptionRequest) {
        val me = studentService.getMe()
        val currentUser = studentRepository.findByIdOrNull(me.id)!!

        repository.saveAll(
            request.newsSourcesAliases.map {
                NewsSourceSubscription(
                    user = currentUser,
                    entity = newsSourceRepository.findOneByAlias(it)
                        ?: throw ResourceNotFoundException("There is no source with alias $it")
                )
            }
        )
    }

    override val supportedEvents: List<Class<out Any>> = listOf(EntityCreatedEvent::class.java)
    override val supportedEntityClasses: List<Class<out BaseEntity<*>>> = listOf(News::class.java)

    override fun getSubscribers(event: BaseEntityEvent): List<Student> {
        val createdNews = newsRepository.findByIdOrNull(event.entityId as Int)!!

        return repository.findAllByEntityId(createdNews.source.id!!).map { it.user }.distinct()
    }

    override fun getNotificationTitle(event: BaseEntityEvent): String {
        val createdNews = newsRepository.findByIdOrNull(event.entityId as Int)!!

        return "Новая новость от ${createdNews.source.type}"
    }

    override fun getNotificationBody(event: BaseEntityEvent): String {
        val createdNews = newsRepository.findByIdOrNull(event.entityId as Int)!!

        return createdNews.title
    }
}