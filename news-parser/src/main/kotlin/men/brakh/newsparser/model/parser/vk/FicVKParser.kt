package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class FicVKParser : VKParser() {
    override val groupId: Int = 128013461
    override val source: NewsSourceDto = NewsSourceDto(type = "ФИК", name = "Группа ФИК ВК", alias = "FIC_VK")
}