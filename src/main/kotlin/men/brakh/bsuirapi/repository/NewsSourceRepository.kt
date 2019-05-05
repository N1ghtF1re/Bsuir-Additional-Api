package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.model.data.NewsSource

interface NewsSourceRepository : Repository<NewsSource> {
    fun find(type: String ?= null, name: String ?= null): List<NewsSource>
}