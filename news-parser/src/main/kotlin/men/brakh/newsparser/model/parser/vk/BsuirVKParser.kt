package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource

class BsuirVKParser : VKParser() {
    override val groupId: Int = 143039548
    override val source: NewsSource = NewsSource(type = "BSUIR", name = "BSUIR VK Group")
}