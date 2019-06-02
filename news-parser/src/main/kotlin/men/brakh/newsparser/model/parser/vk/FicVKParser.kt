package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource

class FicVKParser : VKParser() {
    override val groupId: Int = 128013461
    override val source: NewsSource = NewsSource(type = "FIC", name = "FIC VK Group")
}