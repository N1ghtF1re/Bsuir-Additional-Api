package men.brakh.bsuirapi.repository

import men.brakh.bsuirapi.app.file.model.AbstractFile
import men.brakh.bsuirapi.app.file.model.FileAccessType
import men.brakh.bsuirapi.app.file.model.FileType

interface FileRepository : Repository<AbstractFile> {
    fun find(
            userId: Int? = null,
            fileName: String? = null,
            fileId: String? = null,
            accessType: FileAccessType? = null,
            ownerGroup: String? = null,
            ownedGroupLike: String? = null,
            fileType: FileType? = null,
            parent: Long?
    ): List<AbstractFile>

    fun getParentId(file: AbstractFile): Long?

    fun add(file: AbstractFile, parentId: Long?): AbstractFile
}