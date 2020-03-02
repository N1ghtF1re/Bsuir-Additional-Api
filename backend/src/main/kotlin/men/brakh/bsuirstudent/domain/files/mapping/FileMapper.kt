package men.brakh.bsuirstudent.domain.files.mapping

import men.brakh.bsuirstudent.application.mapping.mapper.CreateDtoMapper
import men.brakh.bsuirstudent.application.mapping.mapper.UpdateDtoMapper
import men.brakh.bsuirstudent.domain.files.*
import men.brakh.bsuirstudent.domain.files.FileType.*
import men.brakh.bsuirstudent.domain.files.repository.FilesRepository
import men.brakh.bsuirstudent.domain.iis.student.repository.StudentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FileMapper(
    private val studentRepository: StudentRepository,
    private val filesRepository: FilesRepository
) : CreateDtoMapper<FileCreateRequest, AbstractFile>, UpdateDtoMapper<UpdateFileRequest, AbstractFile> {
    override fun mapToEntity(createRequest: FileCreateRequest): AbstractFile {
        return when (createRequest.type) {
            FILE -> File(
                fileName = createRequest.fileName,
                accessType = createRequest.accessType,
                mimeType = createRequest.mimeType,
                groupOwner = createRequest.groupOwner,
                parent = createRequest.parent,
                fileExternalId = createRequest.fileExternalId,
                student = studentRepository.findByIdOrNull(createRequest.studentId)!!,
                id = null
            )
            DIRECTORY -> Directory(
                fileName = createRequest.fileName,
                accessType = createRequest.accessType,
                mimeType = createRequest.mimeType,
                groupOwner = createRequest.groupOwner,
                parent = createRequest.parent,
                fileExternalId = createRequest.fileExternalId,
                student = studentRepository.findByIdOrNull(createRequest.studentId)!!,
                id = null
            )
            LINK -> Link(
                fileName = createRequest.fileName,
                accessType = createRequest.accessType,
                mimeType = createRequest.mimeType,
                groupOwner = createRequest.groupOwner,
                parent = createRequest.parent,
                fileExternalId = createRequest.fileExternalId,
                student = studentRepository.findByIdOrNull(createRequest.studentId)!!,
                link = createRequest.link!!,
                id = null
            )
        }
    }

    override fun mapToEntity(entity: AbstractFile, updateRequest: UpdateFileRequest): AbstractFile {
        entity.accessType = updateRequest.accessType ?: entity.accessType
        entity.fileName = updateRequest.fileName ?: entity.fileName

        if (updateRequest.parentId == 0) {
            entity.parent = null
        } else {
            entity.parent = filesRepository.findByIdOrNull(updateRequest.parentId)!! as Directory
        }

        return entity
    }
}