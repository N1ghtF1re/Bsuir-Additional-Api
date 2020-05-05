package men.brakh.bsuirstudent.domain.files

import men.brakh.bsuirstudent.domain.CreateDto
import men.brakh.bsuirstudent.domain.Dto
import men.brakh.bsuirstudent.domain.UpdateDto

data class FileCreateRequest(
    val studentId: Int,
    val type: FileType,
    val fileName: String,
    val fileExternalId: String,
    val accessType: FileAccessType,
    val groupOwner: String,
    val mimeType: String,
    val parent: Directory?,
    var link: String? = null
) : CreateDto

data class FileDto(
    val id: Int?,
    val type: String,

    val studentName: String,
    val studentIisId: Int,

    val fileName: String,
    val accessType: String,
    val groupOwner: String,
    val mimeType: String,

    val parentFileId: Int?,
    var link: String? = null
): Dto

class RootFileDto: Dto {
    val id = "root"
    val fileName = ".."
}

class DownloadFileDto (
    val mimeType: String,
    val fileName: String,
    val byteArray: ByteArray
)

data class CreateDirectoryRequest (
    val fileName: String
)

data class CreateLinkRequest(
    val fileName: String,
    val url: String
)


data class UpdateFileRequest(
    val fileName: String?,
    val parentId: Int?,
    val accessType: FileAccessType?
) : UpdateDto