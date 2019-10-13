package men.brakh.bsuirapi.app.filesstorage.model

import men.brakh.bsuirapicore.model.data.Identifiable
import men.brakh.bsuirapicore.model.data.User
import java.io.FileInputStream
import java.io.FileOutputStream

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
    val user: User
    val fileName: String
    val fileId: String
    val accessType: FileAccessType
    val groupOwner: String
    val fileType: FileType

    fun copy(id: Long): File
}
/**
 * Just File
 */
data class File(override val id: Long = -1,
                override val user: User,
                override val fileName: String,
                override val fileId: String,
                override val accessType: FileAccessType,
                override val groupOwner: String) : AbstractFile {
    override fun copy(id: Long): File {
        return copy(id = id)
    }

    override val fileType: FileType
        get() = FileType.FILE


}

/**
 * Link to a web page.
 */
data class Link(override val id: Long = -1,
                override val user: User,
                override val fileName: String,
                override val fileId: String,
                override val accessType: FileAccessType,
                override val groupOwner: String,
                val url: String) : AbstractFile {
    override val fileType: FileType
        get() = FileType.LINK

    override fun copy(id: Long): File {
        return copy(id = id)
    }
}

/**
 * Directory
 */
data class Directory(override val id: Long = -1,
                     override val user: User,
                     override val fileName: String,
                     override val fileId: String,
                     override val accessType: FileAccessType,
                     override val groupOwner: String,
                     val files: List<AbstractFile>) : AbstractFile {
    override val fileType: FileType
        get() = FileType.DIRECTORY

    override fun copy(id: Long): File {
        return copy(id = id)
    }
}
