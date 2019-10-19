package men.brakh.bsuirapi.app.file.model

import men.brakh.bsuirapicore.model.data.Identifiable

enum class FileAccessType {
    GROUP,
    STREAM
}

enum class FileType {
    FILE,
    DIRECTORY,
    LINK
}

/**
 * Interface for Files
 */
interface AbstractFile : Identifiable{
    val userId: Int
    val fileName: String
    val fileExternalId: String
    val accessType: FileAccessType
    val ownedGroup: String
    val fileType: FileType

    fun copyWithId(id: Long): AbstractFile
    fun copyWithAccessType(accessType: FileAccessType): AbstractFile
    fun copyWithName(filename: String): AbstractFile
}
/**
 * Just File
 */
data class File(override val id: Long = -1,
                override val userId: Int,
                override val fileName: String,
                @Transient override val fileExternalId: String,
                override val accessType: FileAccessType,
                override val ownedGroup: String) : AbstractFile {

    override fun copyWithName(filename: String) = copy(fileName = filename)
    override val fileType: FileType = FileType.FILE
    override fun copyWithId(id: Long): AbstractFile = copy(id = id)
    override fun copyWithAccessType(accessType: FileAccessType): AbstractFile = copy(accessType = accessType)
}

/**
 * Link to a web page.
 */
data class Link(override val id: Long = -1,
                override val userId: Int,
                override val fileName: String,
                @Transient override val fileExternalId: String,
                override val accessType: FileAccessType,
                override val ownedGroup: String,
                val url: String) : AbstractFile {

    override fun copyWithName(filename: String) = copy(fileName = filename)
    override val fileType: FileType = FileType.LINK
    override fun copyWithId(id: Long): AbstractFile = copy(id = id)
    override fun copyWithAccessType(accessType: FileAccessType): AbstractFile = copy(accessType = accessType)

}


/**
 * Directory
 */
data class Directory(override val id: Long = -1,
                     override val userId: Int,
                     override val fileName: String,
                     @Transient override val fileExternalId: String,
                     override val accessType: FileAccessType,
                     override val ownedGroup: String,
                     val files: List<AbstractFile>) : AbstractFile {

    override fun copyWithName(filename: String) = copy(fileName = filename)
    override val fileType: FileType  = FileType.DIRECTORY
    override fun copyWithId(id: Long): AbstractFile = copy(id = id)
    override fun copyWithAccessType(accessType: FileAccessType): AbstractFile = copy(accessType = accessType)
}

