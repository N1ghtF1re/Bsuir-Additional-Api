package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.NewsSource

class BrsmVKParser : VKParser() {
    override val groupId: Int = 27591148
    override val source: NewsSource = NewsSource(type = "OTHER", name = "BSUIR BRSM VK Group")
}