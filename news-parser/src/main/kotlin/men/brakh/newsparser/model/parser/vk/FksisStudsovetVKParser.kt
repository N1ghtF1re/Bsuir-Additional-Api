package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.NewsSource

class FksisStudsovetVKParser : VKParser() {
    override val groupId: Int = 43180910
    override val source: NewsSource = NewsSource(type = "FKSIS", name = "FKSIS Studsovet VK Group")
}