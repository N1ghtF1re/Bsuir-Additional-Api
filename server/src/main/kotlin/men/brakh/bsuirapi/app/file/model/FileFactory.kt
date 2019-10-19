package men.brakh.bsuirapi.app.file.model

object FileFactory {
    fun create (request: FileDbDto) : AbstractFile {
        return when (request.fileType) {
            FileType.FILE -> File(
                    id = request.id,
                    userId = request.userId,
                    fileName = request.fileName,
                    fileExternalId = request.fileExternalId,
                    accessType = request.accessType,
                    ownedGroup = request.ownedGroup
            )
            FileType.DIRECTORY -> Directory(
                    id = request.id,
                    userId = request.userId,
                    fileName = request.fileName,
                    fileExternalId = request.fileExternalId,
                    accessType = request.accessType,
                    ownedGroup = request.ownedGroup,
                    files = request.files!!
            )
            FileType.LINK -> Link(
                    id = request.id,
                    userId = request.userId,
                    fileName = request.fileName,
                    fileExternalId = request.fileExternalId,
                    accessType = request.accessType,
                    ownedGroup = request.ownedGroup,
                    url = request.url!!
            )
        }
    }

}