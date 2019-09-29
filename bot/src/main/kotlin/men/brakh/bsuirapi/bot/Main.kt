package men.brakh.bsuirapi.bot

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

    properties.load(ResourceLoader.load("application.properties"))

    VkBot(threadsCount = properties.getProperty("vk.bot.threads.count").toInt(),
            groupId = properties.getProperty("vk.bot.groupId").toInt(),
            token = properties.getProperty("vk.bot.token"))
}
