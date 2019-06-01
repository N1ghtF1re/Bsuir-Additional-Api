package men.brakh.newsparser.parser.vk

import men.brakh.newsparser.model.NewsSource

class FicVKParser : VKParser() {
    override val groupId: Int = 128013461
    override val source: NewsSource = NewsSource(type = "FIC", name = "FIC VK Group")
}