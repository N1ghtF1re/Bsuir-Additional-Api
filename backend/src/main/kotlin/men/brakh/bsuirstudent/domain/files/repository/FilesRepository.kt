package men.brakh.bsuirstudent.domain.files.repository

import men.brakh.bsuirstudent.domain.files.AbstractFile
import men.brakh.bsuirstudent.domain.files.Directory
import men.brakh.bsuirstudent.domain.files.FileAccessType
import men.brakh.bsuirstudent.domain.files.FileType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface FilesRepository : JpaRepository<AbstractFile, Int>, JpaSpecificationExecutor<AbstractFile> {
    fun findAllByParent(parent: Directory): List<AbstractFile>
    fun findAllByParentAndAccessTypeAndGroupOwner(parent: Directory?, accessType: FileAccessType, groupOwner: String): List<AbstractFile>

    @Query("SELECT f FROM File f WHERE f.parent = :parent AND f.accessType = :accessType AND f.groupOwner LIKE %:groupOwnerLike%")
    fun findAllByParentIdAndAccessTypeAndGroupOwnerLike(parent: Directory?, accessType: FileAccessType, groupOwnerLike: String): List<AbstractFile>

    fun findFirstByParentAndTypeAndFileName(parent: Directory?, type: FileType, fileName: String): AbstractFile?

    fun existsByFileNameAndParent(fileName: String, parent: Directory?): Boolean

    fun findOneByFileExternalId(externalFileId: String): AbstractFile
}