package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource

class FksisStudsovetVKParser : VKParser() {
    override val groupId: Int = 43180910
    override val source: NewsSource = NewsSource(type = "FKSIS", name = "FKSIS Studsovet VK Group")
}
