package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class FksisStudsovetVKParser : VKParser() {
    override val groupId: Int = 43180910
    override val source: NewsSourceDto
            = NewsSourceDto(type = "ФКСиС", name = "Группа студсовета ФКСиС ВК", alias = "STUDSOVET_FKSIS_VK")

}
