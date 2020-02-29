package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class BsuirVKParser : VKParser() {
    override val groupId: Int = 143039548
    override val source: NewsSourceDto
            = NewsSourceDto(type = "БГУИР", name = "Группа БГУИР ВК", alias = "BSUIR_VK")
}