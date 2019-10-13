package men.brakh.bsuirapi.app.filesstorage.model

import java.lang.IllegalArgumentException

object FileFactory {
    fun create (request: FileDbDto) : AbstractFile {
        return when (request.fileType) {
            FileType.FILE -> File(
                    id = request.id,
                    user = request.user,
                    fileName = request.fileName,
                    fileId = request.fileId,
                    accessType = request.accessType,
                    groupOwner = request.groupOwner
            )
            FileType.DIRECTORY -> Directory(
                    id = request.id,
                    user = request.user,
                    fileName = request.fileName,
                    fileId = request.fileId,
                    accessType = request.accessType,
                    groupOwner = request.groupOwner,
                    files = request.files!!
            )
            FileType.LINK -> Link(
                    id = request.id,
                    user = request.user,
                    fileName = request.fileName,
                    fileId = request.fileId,
                    accessType = request.accessType,
                    groupOwner = request.groupOwner,
                    url = request.url!!
            )
        }
    }

    fun create (request: FileUploadingRequest, fileId: String) : AbstractFile {
        return when (request.fileType) {
            FileType.FILE -> File(
                    id = request.id,
                    user = request.user,
                    fileName = request.fileName,
                    fileId = fileId,
                    accessType = request.accessType,
                    groupOwner = request.groupOwner
            )
            FileType.DIRECTORY -> Directory(
                    id = request.id,
                    user = request.user,
                    fileName = request.fileName,
                    fileId = fileId,
                    accessType = request.accessType,
                    groupOwner = request.groupOwner,
                    files = request.fileRequests!!
            )
            FileType.LINK -> throw IllegalArgumentException("Links is unsupported for uploading")
        }
    }
}