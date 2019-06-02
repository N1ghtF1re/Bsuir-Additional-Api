package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource

class FksisVKParser : VKParser() {
    override val groupId: Int = 62992887
    override val source: NewsSource = NewsSource(type = "FKSIS", name = "FKSIS VK Group")
}
