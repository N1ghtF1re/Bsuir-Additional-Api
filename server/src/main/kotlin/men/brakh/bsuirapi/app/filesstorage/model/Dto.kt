package men.brakh.bsuirapi.app.filesstorage.model

import men.brakh.bsuirapicore.model.data.User
import java.io.InputStream

data class FileDbDto(override val user: User,
                     override val fileName: String,
                     override val fileId: String,
                     override val accessType: FileAccessType,
                     override val groupOwner: String,
                     override val fileType: FileType,
                     override val id: Long,
                     val url: String?,
                     val files: List<AbstractFile>?) : AbstractFile {
    override fun copy(id: Long): File {
        return copy(id = id)
    }
}
