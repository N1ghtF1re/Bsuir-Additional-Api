package men.brakh.bsuirapi.app.news.dto

import men.brakh.bsuirapicore.model.data.News

data class NewsListDto(val page: Int, val newsAtPage: Int, val count: Int, val news: List<News>)