package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class IefVKParser : VKParser() {
    override val groupId: Int = 91606386
    override val source: NewsSourceDto = NewsSourceDto(type = "ИЭФ", name = "Группа ИЭФ ВК", alias = "IEF_VK")
}