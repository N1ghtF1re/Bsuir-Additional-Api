package men.brakh.bsuirstudent.application.feedback.api

import men.brakh.bsuirstudent.application.feedback.DeveloperConnectionService
import men.brakh.bsuirstudent.application.logging.LoggingContext
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedbackController(
    private val developerConnectionService: DeveloperConnectionService,
    private val loggingContext: LoggingContext
) {

    @PostMapping("/api/v2/feedback")
    @ResponseBody
    fun sendFeedback(@RequestBody request: FeedbackRequest) {
        developerConnectionService.write(
            """
            Feedback from the user!
                 
            User: ${loggingContext.authenticatedUser}
            SessionId: ${request.sessionId}
            Contacts: ${request.contacts}
            Message: ${request.message}
            """.trimIndent())
    }
}