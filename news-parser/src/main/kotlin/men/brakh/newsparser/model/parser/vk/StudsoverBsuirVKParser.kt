package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class StudsoverBsuirVKParser : VKParser() {
    override val groupId: Int = 99068826
    override val source: NewsSourceDto = NewsSourceDto(type = "БГУИР", name = "Группа студсовета БГУИР ВК", alias = "STUDSOVET_BSUIR")
}