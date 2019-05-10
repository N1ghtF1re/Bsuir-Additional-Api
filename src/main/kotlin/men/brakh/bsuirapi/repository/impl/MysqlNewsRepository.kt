package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.extentions.toSqlDate
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
                publishedAt = resultSet.getDate("publication_date"),
                loadedAt = resultSet.getDate("loading_date"),
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
                stmt.setDate(4, java.sql.Date(entity.publishedAt.time))
                stmt.setDate(5, java.sql.Date(entity.loadedAt.time))
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
                stmt.setDate(4, java.sql.Date(entity.publishedAt.time))
                stmt.setDate(5, java.sql.Date(entity.loadedAt.time))
                stmt.setString(6, entity.url)
                stmt.setString(7, entity.urlToImage)
                stmt.setLong(8, entity.id)

                stmt.execute()
                return entity
            }

        }
    }

    override fun find(title: String?, source: NewsSource?, sources: List<NewsSource>?, content: String?,
                      publishedAfter: Date?, publishedBefore: Date?, loadedAfter: Date?, loadedBefore: Date?,
                      url: String?, urlToImage: String?): List<News> {
        val initQuery = "SELECT * FROM $tableName WHERE"

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

        if(content != null) conditions.add(
                "content = ?" to {stmt, index -> stmt.setString(index, content)}
        )

        if(publishedAfter != null) conditions.add(
                "publication_date > ?"
                        to {stmt, index -> stmt.setDate(index, publishedAfter.toSqlDate())}
        )

        if(publishedBefore != null) conditions.add(
                "publication_date < ?"
                        to {stmt, index -> stmt.setDate(index, publishedBefore.toSqlDate())}
        )

        if(loadedAfter != null) conditions.add(
                "loading_date > ?"
                        to {stmt, index -> stmt.setDate(index, loadedAfter.toSqlDate())}
        )

        if(loadedBefore != null) conditions.add(
                "loading_date < ?"
                        to {stmt, index -> stmt.setDate(index, loadedBefore.toSqlDate())}
        )

        if(url != null) conditions.add(
                "url" to {stmt, index -> stmt.setString(index, url)}
        )

        if(urlToImage != null) conditions.add(
                "image_url" to {stmt, index -> stmt.setString(index, urlToImage)}
        )

        if(conditions.size == 0) return findAll()

        val finalQuery = "$initQuery ${conditions.map{it.first}.joinToString(separator = " AND ")}"

        connection.use {
            val statement = it.prepareStatement(finalQuery)
            statement.use { stmt ->
                for((index, cond) in conditions.withIndex()) {
                    cond.second(stmt, index + 1) // Установка параметров запроса
                }

                return extract(stmt)
            }
        }

    }

}
