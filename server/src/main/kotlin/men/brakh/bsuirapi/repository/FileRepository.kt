package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.app.filesstorage.model.AbstractFile
import men.brakh.bsuirapi.app.filesstorage.model.File
import men.brakh.bsuirapi.app.filesstorage.model.FileAccessType
import men.brakh.bsuirapi.app.filesstorage.model.FileType
import men.brakh.bsuirapicore.model.data.User

interface FileRepository : Repository<AbstractFile> {
    fun find(
            user: User? = null,
            fileName: String? = null,
            fileId: String? = null,
            accessType: FileAccessType? = null,
            ownerGroup: String? = null,
            fileType: FileType? = null,
            parent: Long? = null
    ): List<AbstractFile>

    fun add(file: AbstractFile, parentId: Long?): AbstractFile
}