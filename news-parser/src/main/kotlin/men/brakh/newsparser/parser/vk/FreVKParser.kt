package men.brakh.newsparser.parser.vk

import men.brakh.newsparser.model.NewsSource

class FreVKParser : VKParser() {
    override val groupId: Int = 129667213
    override val source: NewsSource = NewsSource(type = "FRE", name = "FRE VK Group")
}