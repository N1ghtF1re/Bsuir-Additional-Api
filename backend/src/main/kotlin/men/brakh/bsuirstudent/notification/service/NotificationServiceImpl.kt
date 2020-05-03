package men.brakh.bsuirstudent.notification.service

import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import men.brakh.bsuirstudent.domain.iis.student.service.StudentService
import men.brakh.bsuirstudent.notification.NotificationSubscriptionDto
import men.brakh.bsuirstudent.notification.NotificationToken
import men.brakh.bsuirstudent.notification.repository.NotificationTokenRepository
import men.brakh.bsuirstudent.notification.sender.GlobalNotificationSender
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(path = ["/api/v2/push-notifications"])
open class NotificationServiceImpl(
    private val notificationTokenRepository: NotificationTokenRepository,
    private val studentService: StudentService,
    private val studentRepository: StudentRepository,
    private val globalNotificationSender: GlobalNotificationSender
) : NotificationService {
    private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)

    @Transactional
    @PostMapping
    override fun subscribe(@RequestBody notificationSubscriptionDto: NotificationSubscriptionDto) {
        val me = studentService.getMe()

        if (notificationSubscriptionDto.token.trim().isEmpty()) {
            throw IllegalArgumentException("Cannot be null")
        }
        
        if (notificationTokenRepository.countAllByStudentIdAndToken(me.id, notificationSubscriptionDto.token) == 0) {
            logger.info("Student ${me.id} has successfuly added token ${notificationSubscriptionDto.token}")
            notificationTokenRepository.save(
                NotificationToken(
                    token = notificationSubscriptionDto.token,
                    type = notificationSubscriptionDto.tokenType,
                    student = studentRepository.findByIdOrNull(me.id)!!
                )
            )
        } else {
            logger.info("Student ${me.id} already have token ${notificationSubscriptionDto.token}")

        }

       
    }

    @Transactional
    @DeleteMapping("/{token}")
    override fun usSubscribe(@PathVariable(name = "token") token: String) {
        val me = studentService.getMe()
        logger.info("Student ${me.id} successfuly removed token $token")

        notificationTokenRepository.deleteAllByStudentIdAndToken(
            me.id,
            token
        )
    }

    @Transactional(readOnly = true)
    @PostMapping("/test")
    override fun test() {
        val me = studentService.getMe()

        globalNotificationSender.send(studentRepository.findByIdOrNull(me.id)!!,
            "TEST" + Random().nextInt(), "TEST" + Random().nextInt())
    }
}