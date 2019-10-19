package men.brakh.bsuirapi.app.file.model

data class FileDbDto(override val userId: Int,
                     override val fileName: String,
                     override val fileExternalId: String,
                     override val accessType: FileAccessType,
                     override val ownedGroup: String,
                     override val fileType: FileType,
                     override val id: Long,
                     val url: String?,
                     val files: List<AbstractFile>?) : AbstractFile {
    override fun copyWithAccessType(accessType: FileAccessType): AbstractFile {
        return copyWithAccessType(accessType = accessType)
    }
    override fun copyWithName(filename: String) = copy(fileName = filename)


    override fun copyWithId(id: Long): AbstractFile {
        return copyWithId(id = id)
    }
}

data class DirectoryCreateRequest(
        val fileName: String
)

data class LinkCreateRequest(
        val fileName: String,
        val url: String
)

data class FileUpdateRequest(
        val accessType: FileAccessType
)