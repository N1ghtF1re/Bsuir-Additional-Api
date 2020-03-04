package men.brakh.bsuirstudent.notification.sender.apple

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "notifications.apple")
open class AppleNotificationConfiguration {
    lateinit var p8Path: String

    lateinit var teamId: String
    lateinit var keyId: String

    lateinit var applicationName: String
}