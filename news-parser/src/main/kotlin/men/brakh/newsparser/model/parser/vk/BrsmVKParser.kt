package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class BrsmVKParser : VKParser() {
    override val groupId: Int = 27591148
    override val source: NewsSourceDto
            = NewsSourceDto(type = "Другое", name = "Группа БРСМ ВК", alias = "BDSM_BSUR_VK")
}