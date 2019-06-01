package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.NewsSource

class StudsoverBsuirVKParser : VKParser() {
    override val groupId: Int = 99068826
    override val source: NewsSource = NewsSource(type = "BSUIR", name = "BSUIR Studsovet VK Group")
}