package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.app.file.model.*
import men.brakh.bsuirapi.repository.FileRepository
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlFileRepository(tableName: String)
            : MysqlRepository<AbstractFile>(tableName), FileRepository {
    constructor() : this("files")

    private val logger = LoggerFactory.getLogger(MysqlFileRepository::class.java)

    override fun extractor(resultSet: ResultSet): AbstractFile? {
        return FileFactory.create(
                FileDbDto(
                        userId = resultSet.getInt("user_id"),
                        fileName = resultSet.getString("filename"),
                        fileExternalId = resultSet.getString("fileId"),
                        accessType = FileAccessType.valueOf(resultSet.getString("access_type")),
                        ownedGroup = resultSet.getString("group_owner"),
                        fileType = FileType.valueOf(resultSet.getString("file_type")),
                        url = resultSet.getString("link"),
                        files = find(parent = resultSet.getLong("id")),
                        id = resultSet.getLong("id")
                )
        )
    }

    override fun add(file: AbstractFile, parentId: Long?): AbstractFile {
        connection.use {

            val statement = it.prepareStatement("INSERT INTO `$tableName` " +
                    "(`user_id`, `filename`, `fileId`, `access_type`, `group_owner`, `file_type`, `link`, `parent`) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)


            statement.use {stmt: PreparedStatement ->
                stmt.setInt(1, file.userId)
                stmt.setString(2, file.fileName)
                stmt.setString(3, file.fileExternalId)
                stmt.setString(4, file.accessType.toString())
                stmt.setString(5, file.ownedGroup)
                stmt.setString(6, file.fileType.toString())
                stmt.setString(7, if (file is Link) file.url else null)
                if (parentId == null) {
                    stmt.setObject(8, null)
                } else {
                    stmt.setLong(8, parentId)
                }

                stmt.executeUpdate()

                if (file is Directory) {
                    return file.copy(
                            id = stmt.generatedId,
                            files = file.files.map { childFile -> add(childFile, stmt.generatedId) }
                    )
                }

                return file.copyWithId(id = stmt.generatedId)
            }
        }
    }


    override fun add(entity: AbstractFile): AbstractFile {
        return add(entity, null)
    }

    override fun getParentId(file: AbstractFile): Long? {
        connection.use {
            val statement = it.prepareStatement("SELECT parent FROM $tableName WHERE id = ?")
            statement.use { stmt ->
                stmt.setLong(1, file.id)
                val resultSet = stmt.executeQuery()
                resultSet.use {
                    return if (resultSet.next()) {
                        val res = resultSet.getLong(1)
                        if (res == 0L) null else res
                    } else {
                        null
                    }
                }
            }
        }

    }

    fun update(file: AbstractFile, connection: Connection): AbstractFile {
        connection.let {

            val statement = it.prepareStatement("UPDATE `$tableName` " +
                    "SET `user_id` = ?, `filename` = ?, `fileId` = ?, `access_type` = ?, " +
                    "`group_owner` = ?, `file_type` = ?, `link` = ? " +
                    "WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS)


            statement.use { stmt: PreparedStatement ->
                stmt.setInt(1, file.userId)
                stmt.setString(2, file.fileName)
                stmt.setString(3, file.fileExternalId)
                stmt.setString(4, file.accessType.toString())
                stmt.setString(5, file.ownedGroup)
                stmt.setString(6, file.fileType.toString())
                stmt.setString(7, if (file is Link) file.url else null)
                stmt.setLong(8, file.id)

                stmt.execute()

                if (file is Directory) {
                    val children = file.files.map { child ->
                        if (file.id > 0) {
                            update(child, connection)
                        } else {
                            add(child)
                        }
                    }

                    return file.copy(files = children)
                }

                return file
            }
        }
    }

    override fun update(entity: AbstractFile): AbstractFile {
        connection.use {
            it.autoCommit = false
            logger.info("Starting file updating transaction")
            try {
                val result = update(entity, connection)
                it.commit()
                logger.info("Successful transaction")
                return result
            } catch (e: Exception) {
                it.rollback()
                logger.error("Transaction failed", e)
                throw e
            }
        }
    }

    override fun find(userId: Int?,
                      fileName: String?,
                      fileId: String?,
                      accessType: FileAccessType?,
                      ownerGroup: String?,
                      ownedGroupLike: String?,
                      fileType: FileType?,
                      parent: Long?): List<AbstractFile> {

        val initQuery = "SELECT * FROM $tableName "

        val conditions=
                mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()

        if(userId != null) conditions.add(
                "user_id = ?" to {stmt, index -> stmt.setInt(index, userId)}
        )

        if(fileName != null) conditions.add(
                "filename = ?" to {stmt, index -> stmt.setString(index, fileName)}
        )

        if(fileId != null) conditions.add(
                "fileId = ?" to {stmt, index -> stmt.setString(index, fileId)}
        )

        if(accessType != null) conditions.add(
                "access_type = ?" to {stmt, index -> stmt.setString(index, accessType.toString())}
        )

        if(ownerGroup != null) conditions.add(
                "group_owner = ?" to {stmt, index -> stmt.setString(index, ownerGroup)}
        )

        if (ownedGroupLike != null) conditions.add(
                "group_owner LIKE ?" to {stmt, index -> stmt.setString(index, ownedGroupLike)}
        )

        if(fileType != null) conditions.add(
                "file_type = ?" to {stmt, index -> stmt.setString(index, fileType.toString())}
        )

        if (parent == null) {
            conditions.add(
                    "parent IS ?" to {stmt, index -> stmt.setObject(index, null)}
            )
        } else {
            conditions.add(
                    "parent = ?" to {stmt, index -> stmt.setLong(index, parent)}
            )
        }

        if(conditions.size == 0) return findAll()

        val finalQuery = "$initQuery WHERE ${conditions.joinToString(separator = " AND ") { it.first }}"

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