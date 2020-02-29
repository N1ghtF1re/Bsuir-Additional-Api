package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class FituVKParser : VKParser() {
    override val groupId: Int = 30014419
    override val source: NewsSourceDto = NewsSourceDto(type = "ФИТУ", name = "Группа ФИТУ ВК", alias = "FITU_VK")
}