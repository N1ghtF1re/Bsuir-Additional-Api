package men.brakh.bsuirstudent.application.logging

import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.util.*

private const val SESSION_ID = "SESSION-ID";
private const val URI = "URI";
private const val USER = "USER";

@Component
class LoggingContext {
    var sessionId
        set(value) = MDC.put(SESSION_ID, value)
        get() = MDC.get(SESSION_ID)
    var uri
        set(value) = MDC.put(URI, value)
        get() = MDC.get(URI)
    var authenticatedUser
        set(value) = MDC.put(USER, value)
        get() = MDC.get(USER)
    fun generateSessionID() {
        sessionId = UUID.randomUUID().toString()
    }

    fun clear() {
        MDC.clear()
    }
}