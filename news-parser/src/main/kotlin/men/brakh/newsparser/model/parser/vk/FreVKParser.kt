package men.brakh.newsparser.model.parser.vk

import men.brakh.bsuirapicore.model.data.NewsSource

class FreVKParser : VKParser() {
    override val groupId: Int = 129667213
    override val source: NewsSource = NewsSource(type = "FRE", name = "FRE VK Group")
}