package men.brakh.bsuirstudent.application.feedback.api

data class FeedbackRequest(
    val sessionId: String?,
    val message: String?,
    val contacts: String?,
    val issueType: String?
)