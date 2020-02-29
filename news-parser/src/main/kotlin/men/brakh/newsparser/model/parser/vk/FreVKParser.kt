package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto

class FreVKParser : VKParser() {
    override val groupId: Int = 129667213
    override val source: NewsSourceDto = NewsSourceDto(type = "ФРЭ", name = "Группа ФРЭ ВК", alias = "FRE_VK")
}