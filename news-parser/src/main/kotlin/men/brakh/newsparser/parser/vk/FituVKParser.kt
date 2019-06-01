package men.brakh.newsparser.parser.vk

import men.brakh.newsparser.model.NewsSource

class FituVKParser : VKParser() {
    override val groupId: Int = 30014419
    override val source: NewsSource = NewsSource(type = "FITU", name = "FITU VK Group")
}