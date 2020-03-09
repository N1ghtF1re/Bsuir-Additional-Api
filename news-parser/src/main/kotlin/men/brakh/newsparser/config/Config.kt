package men.brakh.newsparser.config

import java.util.*

object Config {
    val vkAppId: Int
    val vkAppToken: String

    val apiHost: String
    val apiToken: String

    init {

        vkAppId = System.getenv("BSUIR_STUDENT_NEWS_PARSER_VK_APP_ID").toInt()
        vkAppToken = System.getenv("BSUIR_STUDENT_NEWS_PARSER_VK_APP_TOKEN")

        apiHost = System.getenv("BSUIR_STUDENT_API_HOST")
        apiToken = System.getenv("BSUIR_STUDENT_API_TOKEN")
    }
}