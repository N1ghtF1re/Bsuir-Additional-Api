package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto

class FksisVKParser : VKParser() {
    override val groupId: Int = 62992887
    override val source: NewsSourceDto
            = NewsSourceDto(type = "ФКСиС", name = "Группа ФКСиС ВК", alias = "FKSIS_VK")
}
