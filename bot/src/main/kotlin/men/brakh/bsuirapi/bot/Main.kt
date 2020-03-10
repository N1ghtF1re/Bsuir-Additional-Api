package men.brakh.bsuirapi.bot

import men.brakh.bsuirapi.bot.config.ApplicationConfig
import men.brakh.bsuirapi.bot.vk.VkBot
import java.io.InputStream
import java.util.*

object ResourceLoader {
    fun load(name: String): InputStream? {
        return ResourceLoader::javaClass::class.java.classLoader.getResourceAsStream(name)
    }
}

fun main() {
    val properties = Properties()


    VkBot(threadsCount = ApplicationConfig.threadsCount,
            groupId = ApplicationConfig.vkBotId,
            token = ApplicationConfig.vkToken)
}
