package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.NewsSource

class ProfcomVKParser : VKParser() {
    override val groupId: Int = 92161895
    override val source: NewsSource = NewsSource(type = "OTHER", name = "BSUIR Profcom VK Group")
}