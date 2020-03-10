package men.brakh.bsuirapi.bot.config

object ApplicationConfig {
    val vkBotId = System.getenv("BSUIR_STUDENT_VK_GROUP_ID").toInt()
    val vkToken = System.getenv("BSUIR_STUDENT_VK_TOKEN")
    val threadsCount = 5
    val apiHost = System.getenv("BSUIR_STUDENT_API_HOST")
}