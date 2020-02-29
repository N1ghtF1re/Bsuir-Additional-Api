package men.brakh.newsparser.model.parser.vk

import men.brakh.newsparser.model.dto.NewsSourceDto


class ProfcomVKParser : VKParser() {
    override val groupId: Int = 92161895
    override val source: NewsSourceDto
            = NewsSourceDto(type = "Другое", name = "Группа профкома БГУИР ВК", alias = "PROFKOM_BSUIR")
}