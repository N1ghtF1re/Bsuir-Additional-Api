package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource


class FituVKParser : VKParser() {
    override val groupId: Int = 30014419
    override val source: NewsSource = NewsSource(type = "FITU", name = "FITU VK Group")
}