package men.brakh.bsuirapi.model.dto

import men.brakh.bsuirapi.model.data.News

data class NewsListDto(val page: Int, val newsAtPage: Int, val count: Int, val news: List<News>)