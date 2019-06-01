package men.brakh.newsparser.config

import java.util.*

object Config {
    val vkAppId: Int
    val vkAppToken: String

    init {
        val props = Properties()
        val input = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties")
        input.use {
            props.load(it)
        }

        vkAppId = props.getProperty("vk.app.id").toInt()
        vkAppToken = props.getProperty("vk.app.token")
    }
}