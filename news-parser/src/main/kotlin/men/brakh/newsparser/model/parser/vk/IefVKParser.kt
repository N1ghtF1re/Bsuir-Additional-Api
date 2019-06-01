package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.NewsSource

class IefVKParser : VKParser() {
    override val groupId: Int = 91606386
    override val source: NewsSource = NewsSource(type = "IEF", name = "IEF VK Group")
}