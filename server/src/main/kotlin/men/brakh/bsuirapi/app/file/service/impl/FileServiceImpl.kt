package men.brakh.bsuirapi.app.file.service.impl

import men.brakh.bsuirapi.AccessDeniedException
import men.brakh.bsuirapi.EntityAlreadyExistsException
import men.brakh.bsuirapi.NotFoundException
import men.brakh.bsuirapi.app.file.model.*
import men.brakh.bsuirapi.app.file.service.FileService
import men.brakh.bsuirapi.inrfastructure.authorization.authorized
import men.brakh.bsuirapi.inrfastructure.externalStorage.service.ExternalFilesStorageService
import men.brakh.bsuirapi.repository.FileRepository
import men.brakh.bsuirapicore.model.data.User
import java.io.InputStream
import java.util.*

class FileServiceImpl(private val externalFilesStorageService: ExternalFilesStorageService,
                      private val fileRepository: FileRepository) : FileService {

    private val defaultAccessType = FileAccessType.GROUP


    override fun uploadFile(filename: String, inputStream: InputStream, parentId: Long?): File {
        var uploadedFile: File? = null

        authorized { user ->
            throwIfFileExists(filename, parentId, user)
            val parentFileExternalId = getParentFileExternalId(parentId, user)

            val fileExternalId = externalFilesStorageService.uploadFile(
                    filename = filename,
                    inputStream = inputStream,
                    parentExternalId = parentFileExternalId
            )

            uploadedFile = fileRepository.add(File(
                    fileExternalId = fileExternalId,
                    userId = user.id,
                    ownedGroup = user.education.group,
                    fileName = filename,
                    accessType = defaultAccessType
            ), parentId) as File
        }

        return uploadedFile ?: throw IllegalArgumentException("Unable to upload the file. Try again later")
    }

    override fun uploadLink(filename: String, url: String, parentId: Long?): Link {
        authorized { user ->
            throwIfFileExists(filename, parentId, user)

            return fileRepository.add(
                    Link(
                            fileName = filename,
                            accessType = defaultAccessType,
                            url = url,
                            fileExternalId = generateExternalId(),
                            ownedGroup = user.education.group,
                            userId = user.id
                    ),
                    parentId
            ) as Link
        }

        throw IllegalArgumentException("Unable to upload the file. Try again later")
    }

    private fun changeAccessTypeInList(list: List<AbstractFile>,
                                       accessType: FileAccessType): List<AbstractFile> {
        return list.map { file ->
            val result = if (file is Directory) {
                file.copy(files = changeAccessTypeInList(file.files, accessType), accessType = accessType)
            } else {
                file.copyWithAccessType(accessType)
            }
            result
        }
    }

    override fun updateFileAccess(id: Long, newAccessType: FileAccessType): AbstractFile {
        authorized { user ->
            val file = fileRepository.findById(id)
                    ?: throw NotFoundException("File with id $id isn't found")

            if (file.userId != user.id)
                throw AccessDeniedException("Only file's owner can do it")

            var parentId = fileRepository.getParentId(file)

            // Make all parents available
            while (parentId != null) {
                val parent = fileRepository.findById(parentId)!!
                fileRepository.update(parent.copyWithAccessType(newAccessType))
                parentId = fileRepository.getParentId(parent)
            }

            return if (file is Directory) {
                fileRepository.update(file.copy(accessType = newAccessType,
                        files = changeAccessTypeInList(file.files, newAccessType))
                )
            } else {
                fileRepository.update(file.copyWithAccessType(accessType = newAccessType))
            }
        }

        throw IllegalArgumentException("Unable to update the file. Try again later")
    }



    override fun createDirectory(filename: String, parentId: Long?): Directory {
        authorized { user ->
            throwIfFileExists(filename, parentId, user)

            val parentFileExternalId = getParentFileExternalId(parentId, user)


            val fileExternalId: String = externalFilesStorageService.makeDir(
                    filename, parentFileExternalId
            )

            return fileRepository.add(
                    Directory(
                            fileName = filename,
                            ownedGroup = user.education.group,
                            accessType = defaultAccessType,
                            userId = user.id,
                            files = listOf(),
                            fileExternalId = fileExternalId
                    ),
                    parentId
            ) as Directory
        }

        throw IllegalArgumentException("Unable to upload the file. Try again later")
    }

    override fun getAvailableFiles(parentId: Long?): List<AbstractFile> {
        authorized { user ->
            val filesAvailableForGroup = fileRepository.find(
                    accessType = FileAccessType.GROUP,
                    ownerGroup = user.education.group,
                    parent = parentId
            )

            val filesAvailableForStream = fileRepository.find(
                    accessType = FileAccessType.STREAM,
                    ownedGroupLike = user.education.group.dropLast(1) + "%",
                    parent = parentId
            )

            val allFiles = (filesAvailableForGroup + filesAvailableForStream).sortedBy { it.id }
            return allFiles
                    .groupBy { it.fileName }
                    .flatMap { (filename, files) ->
                        if (files.size == 1) {
                            files
                        } else {
                            files.map { it.copyWithName("$filename (${it.ownedGroup})") }
                        }
                    }
        }

        throw IllegalArgumentException("Unable to get files. Try again later")
    }

    override fun getFile(id: Long): AbstractFile {
        authorized { user ->
            val file = fileRepository.findById(id)
                    ?: throw NotFoundException("File with id $id isn't found")

            file.ifAvaiable(user) {

                return if (file is Directory) {
                    file.copy(files = getAvailableFiles(file.id))
                } else {
                    file
                }
            }
        }

        throw IllegalArgumentException("Unable to get file. Try again later")
    }

    override fun downloadFile(id: Long): Pair<String, ByteArray> {
        authorized { user ->
            val file = fileRepository.findById(id)
                    ?: throw NotFoundException("File with id $id isn't found")

            if (file.fileType != FileType.FILE) {
                throw IllegalArgumentException("You can only download file")
            }

            file.ifAvaiable(user) {
                return externalFilesStorageService.getFile(file.fileExternalId)
            }

        }

        throw IllegalArgumentException("Unable to get file. Try again later")
    }

    private fun generateExternalId(): String = UUID.randomUUID().toString()

    private fun getParentFileExternalId(parentId: Long?, user: User): String {
        return if (parentId == null) {
            val dir = fileRepository.find(
                    parent = null,
                    fileType = FileType.DIRECTORY,
                    fileName = user.education.group
            ).firstOrNull()
            dir?.fileExternalId ?: externalFilesStorageService.makeDir(user.education.group)
        } else {
            fileRepository.findById(parentId)?.fileExternalId
                    ?: throw NotFoundException("Parent file isn't exist")
        }
    }

    private fun throwIfFileExists(filename: String, parentId: Long?, user: User) {
        val existingFiles = fileRepository.find(fileName = filename,
                parent = parentId)

        if (existingFiles.any { it.isAvailable(user) }) {
            throw EntityAlreadyExistsException("File with name $filename in directory " +
                    "${if (parentId == null) "root" else " with id $parentId"} is already exists")
        }
    }

}