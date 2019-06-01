package men.brakh.newsparser.parser.vk

import men.brakh.newsparser.model.NewsSource

class FksisVKParser : VKParser() {
    override val groupId: Int = 62992887
    override val source: NewsSource = NewsSource(type = "FKSIS", name = "FKSIS VK Group")
}
