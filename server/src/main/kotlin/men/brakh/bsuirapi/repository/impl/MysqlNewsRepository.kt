package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.extentions.toDate
import men.brakh.bsuirapi.extentions.toTimestamp
import men.brakh.bsuirapi.model.data.News
import men.brakh.bsuirapi.model.data.NewsSource
import men.brakh.bsuirapi.repository.NewsRepository
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.util.*

class MysqlNewsRepository(tableName: String) : MysqlRepository<News>(tableName), NewsRepository  {
    private val srcRepo = Config.newsSourceRepository

    constructor() : this("news")


    override fun extractor(resultSet: ResultSet): News? {
        val src: NewsSource = srcRepo.findById(resultSet.getLong("source_id")) ?: return null
        return News(
                id = resultSet.getLong("id"),
                title = resultSet.getString("title"),
                source = src,
                content = resultSet.getString("content"),
                publishedAt = resultSet.getTimestamp("publication_date").toDate(),
                loadedAt = resultSet.getTimestamp("loading_date").toDate(),
                url = resultSet.getString("url"),
                urlToImage = resultSet.getString("image_url")
        )
    }

    override fun add(entity: News): News {
        connection.use { con ->
            val statement = con.prepareStatement("INSERT INTO `$tableName` (`title`, `source_id`, " +
                    "`content`, `publication_date`, `loading_date`, `url`, `image_url`) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)

            statement.use { stmt ->
                stmt.setString(1, entity.title)
                stmt.setLong(2, entity.source.id)
                stmt.setString(3, entity.content)
                stmt.setTimestamp(4, entity.publishedAt.toTimestamp())
                stmt.setTimestamp(5, entity.loadedAt.toTimestamp())
                stmt.setString(6, entity.url)
                stmt.setString(7, entity.urlToImage)

                stmt.executeUpdate()

                return entity.copy(id = stmt.generatedId)
            }

        }
    }

    override fun update(entity: News): News {
        connection.use { con ->
            val statement= con.prepareStatement("UPDATE $tableName SET `title` = ?, `source_id` = ?, " +
                    "`content` = ?, `publication_date` = ?, `loading_date` = ?, `url` = ?, `image_url` = ? WHERE `id` = ? ")

            statement.use { stmt ->
                stmt.setString(1, entity.title)
                stmt.setLong(2, entity.source.id)
                stmt.setString(3, entity.content)
                stmt.setTimestamp(4, entity.publishedAt.toTimestamp())
                stmt.setTimestamp(5, entity.loadedAt.toTimestamp())
                stmt.setString(6, entity.url)
                stmt.setString(7, entity.urlToImage)
                stmt.setLong(8, entity.id)

                stmt.execute()
                return entity
            }

        }
    }

    override fun find(title: String?, source: NewsSource?, sources: List<NewsSource>?, contentLike: String?,
                      publishedAfter: Date?, publishedBefore: Date?, loadedAfter: Date?, loadedBefore: Date?,
                      url: String?, urlToImage: String?, page: Int?, newsAtPage: Int?): List<News> {
        val initQuery = "SELECT * FROM $tableName "

        val conditions=
                mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()

        if(title != null) conditions.add(
                "title = ?" to {stmt, index -> stmt.setString(index, title)}
        )

        if(source != null) conditions.add(
                "source_id = ?" to {stmt, index -> stmt.setLong(index, source.id)}
        )

        if(sources != null) {
            val sourcesString = sources.map { it.id }.joinToString(separator = ", ")
            conditions.add(
                    "source_id IN ($sourcesString) AND id > ?" to { stmt, index -> stmt.setLong(index, 0)}

            )
        }

        if(contentLike != null) conditions.add(
                "content LIKE ?" to {stmt, index -> stmt.setString(index, "%$contentLike%")}
        )

        if(publishedAfter != null) conditions.add(
                "publication_date >= ?"
                        to {stmt, index -> stmt.setTimestamp(index, publishedAfter.toTimestamp())}
        )

        if(publishedBefore != null) conditions.add(
                "publication_date <= ?"
                        to {stmt, index -> stmt.setTimestamp(index, publishedBefore.toTimestamp())}
        )

        if(loadedAfter != null) conditions.add(
                "loading_date >= ?"
                        to {stmt, index -> stmt.setTimestamp(index, loadedAfter.toTimestamp())}
        )

        if(loadedBefore != null) conditions.add(
                "loading_date <= ?"
                        to {stmt, index -> stmt.setTimestamp(index, loadedBefore.toTimestamp())}
        )

        if(url != null) conditions.add(
                "url = ?" to {stmt, index -> stmt.setString(index, url)}
        )

        if(urlToImage != null) conditions.add(
                "image_url = ?" to {stmt, index -> stmt.setString(index, urlToImage)}
        )

        val limit = if(page != null && newsAtPage != null) {
            "ORDER BY publication_date DESC LIMIT ?, ?"
        } else {
            ""
        }

        val where = if(conditions.count() > 0) {
            "WHERE"
        } else {
            ""
        }
        val finalQuery = "$initQuery $where ${conditions.map{it.first}.joinToString(separator = " AND ")} $limit"

        connection.use {
            val statement = it.prepareStatement(finalQuery)
            statement.use { stmt ->
                for((index, cond) in conditions.withIndex()) {
                    cond.second(stmt, index + 1) // Установка параметров запроса
                }

                if(limit != "") {
                    val startIndex = (page!! - 1) * newsAtPage!!
                    val lastIndex = conditions.count()
                    stmt.setInt(lastIndex + 1, startIndex)
                    stmt.setInt(lastIndex + 2, newsAtPage)
                }

                return extract(stmt)
            }
        }

    }

}
