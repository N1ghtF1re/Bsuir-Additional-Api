package men.brakh.bsuirapi.repository.impl

import men.brakh.bsuirapi.app.bsuirapi.BsuirApi
import men.brakh.bsuirapi.app.filesstorage.model.*
import men.brakh.bsuirapi.repository.FileRepository
import men.brakh.bsuirapicore.model.data.User
import org.slf4j.LoggerFactory
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class MysqlFileRepository(tableName: String): MysqlRepository<AbstractFile>(tableName), FileRepository {
    constructor() : this("fileRequests")

    private val bsuirApi = BsuirApi

    override fun extractor(resultSet: ResultSet): AbstractFile? {
        return FileFactory.create(
                FileFactory.FileCreationRequest(
                        user = bsuirApi.getUserCV(resultSet.getInt("user_id")).toUserInfoObject(),
                        fileName = resultSet.getString("filename"),
                        fileId = resultSet.getString("fileId"),
                        accessType = FileAccessType.valueOf(resultSet.getString("access_type")),
                        groupOwner = resultSet.getString("group_owner"),
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
                stmt.setInt(1, file.user.id)
                stmt.setString(2, file.fileName)
                stmt.setString(3, file.fileId)
                stmt.setString(4, file.accessType.toString())
                stmt.setString(5, file.groupOwner)
                stmt.setString(6, file.fileType.toString())
                stmt.setString(7, if (file is Link) file.url else null)
                if (parentId == null) {
                    stmt.setObject(8, null)
                } else {
                    stmt.setLong(0, parentId)
                }

                stmt.executeUpdate()

                if (file is Directory) {
                    return file.copy(
                            id = stmt.generatedId,
                            files = file.files.map { childFile -> add(childFile, stmt.generatedId) }
                    )
                }

                return file.copy(id = stmt.generatedId)
            }
        }
    }


    override fun add(entity: AbstractFile): AbstractFile {
        return add(entity, null)
    }


    override fun update(file: AbstractFile): AbstractFile {
        TODO("At this moment file updating isn't work")
    }

    override fun find(user: User?,
                      fileName: String?,
                      fileId: String?,
                      accessType: FileAccessType?,
                      ownerGroup: String?,
                      fileType: FileType?,
                      parent: Long?): List<AbstractFile> {

        val initQuery = "SELECT * FROM $tableName "

        val conditions=
                mutableListOf<Pair<String, (stmt: PreparedStatement, index: Int) -> Unit>>()

        if(user != null) conditions.add(
                "user_id = ?" to {stmt, index -> stmt.setInt(index, user.id)}
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

        if(accessType != null) conditions.add(
                "group_owner = ?" to {stmt, index -> stmt.setString(index, ownerGroup)}
        )

        if(accessType != null) conditions.add(
                "file_type = ?" to {stmt, index -> stmt.setString(index, fileType.toString())}
        )

        if(parent != null) conditions.add(
                "parent = ?" to {stmt, index -> stmt.setLong(index, parent)}
        )

        if(conditions.size == 0) return findAll()

        val finalQuery = "$initQuery ${conditions.joinToString(separator = " AND ") { it.first }}"

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