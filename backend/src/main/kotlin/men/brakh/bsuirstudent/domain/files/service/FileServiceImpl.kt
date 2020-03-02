package men.brakh.bsuirstudent.domain.files.service

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import men.brakh.bsuirstudent.application.template.CreateTemplate
import men.brakh.bsuirstudent.application.template.GetTemplate
import men.brakh.bsuirstudent.application.template.UpdateTemplate
import men.brakh.bsuirstudent.domain.files.*
import men.brakh.bsuirstudent.domain.files.externalStorage.ExternalFilesStorageService
import men.brakh.bsuirstudent.domain.files.mapping.FileMapper
import men.brakh.bsuirstudent.domain.files.mapping.FilePresenter
import men.brakh.bsuirstudent.domain.files.repository.FilesRepository
import men.brakh.bsuirstudent.domain.files.secutiry.FilesPermissionEvaluator
import men.brakh.bsuirstudent.domain.iis.student.service.StudentService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.InputStream
import java.net.URLEncoder
import java.util.*

@Service
open class FileServiceImpl(private val externalFilesStorageService: ExternalFilesStorageService,
                      private val fileRepository: FilesRepository,
                      private val studentService: StudentService,
                      private val fileMapper: FileMapper,
                      private val filePresenter: FilePresenter,
                      private val filesPermissionEvaluator: FilesPermissionEvaluator
) : FileService {
    private val createTemplate = CreateTemplate(
        fileRepository,
        fileMapper,
        filePresenter
    )
    private val updateTemplate = UpdateTemplate(
        fileRepository,
        fileMapper,
        filePresenter
    )
    private val getTemplate = GetTemplate(
        filePresenter,
        fileRepository
    )
    private val defaultAccessType = FileAccessType.GROUP


    @Transactional
    @PreAuthorize("hasPermission(#parentId, 'File', 'UPLOAD_FILE')")
    override fun uploadFile(filename: String, inputStream: InputStream, parentId: Int?, contentType: String): FileDto {
        val student = studentService.getMe()
        val parent = getParent(parentId)



        val uploadedFileDto = externalFilesStorageService.uploadFile(
            filename = filename,
            inputStream = inputStream,
            contentType = contentType
        )

        val fileCreateRequest = FileCreateRequest(
            fileExternalId = uploadedFileDto.id,
            mimeType = uploadedFileDto.mimeType,
            fileName = filename,
            accessType = defaultAccessType,
            groupOwner = student.educationInfo.group,
            studentId = student.id,
            parent = parent,
            type = FileType.FILE
        )

        return createTemplate.save(fileCreateRequest, FileDto::class.java)
    }

    @PreAuthorize("hasPermission(#parentId, 'File', 'UPLOAD_FILE')")
    @Transactional
    override fun uploadLink(filename: String, url: String, parentId: Int?): FileDto {
        val student = studentService.getMe()
        val parent = getParent(parentId)


        val fileCreateRequest = FileCreateRequest(
            fileExternalId = generateExternalId(),
            mimeType = "brakh/link",
            fileName = filename,
            accessType = defaultAccessType,
            groupOwner = student.educationInfo.group,
            studentId = student.id,
            parent = parent,
            type = FileType.LINK,
            link = url
        )

        return createTemplate.save(fileCreateRequest, FileDto::class.java)
    }

    @PreAuthorize("hasPermission(#parentId, 'File', 'UPLOAD_FILE')")
    @Transactional
    override fun createDirectory(filename: String, parentId: Int?): FileDto {
        val student = studentService.getMe()
        val parent = getParent(parentId)

        val fileCreateRequest = FileCreateRequest(
            fileExternalId = generateExternalId(),
            mimeType = "inode/directory",
            fileName = filename,
            accessType = defaultAccessType,
            groupOwner = student.educationInfo.group,
            studentId = student.id,
            parent = parent,
            type = FileType.DIRECTORY
        )

        return createTemplate.save(fileCreateRequest, FileDto::class.java)
    }

    @PreAuthorize("hasPermission(#parentId, 'File', 'READ')")
    @Transactional(readOnly = true)
    override fun getAvailableFiles(parentId: Int?): List<FileDto> {
        val parent = getParent(parentId)
        val student = studentService.getMe()

        val filesAvailableForGroup = fileRepository.findAllByParentAndAccessTypeAndGroupOwner(
            accessType = FileAccessType.GROUP,
            groupOwner = student.educationInfo.group,
            parent = parent
        )

        val filesAvailableForStream = fileRepository.findAllByParentIdAndAccessTypeAndGroupOwnerLike(
            accessType = FileAccessType.STREAM,
            groupOwnerLike = student.educationInfo.group.dropLast(1),
            parent = parent
        )

        val allFiles = (filesAvailableForGroup + filesAvailableForStream).distinct().sortedBy { it.id }

        return filePresenter.mapListToDto(allFiles, FileDto::class.java)
    }

    @PreAuthorize("hasPermission(#id, 'File', 'READ')")
    @Transactional(readOnly = true)
    override fun getFile(id: Int): FileDto {
        return getTemplate.getById(id, FileDto::class.java)
    }

    @PreAuthorize("hasPermission(#id, 'File', 'READ')")
    @Transactional(readOnly = true)
    override fun downloadFile(id: Int): DownloadFileDto {
        val file = fileRepository.findByIdOrNull(id)
            ?: throw ResourceNotFoundException("File with id $id isn't found")

        if (file.type != FileType.FILE) {
            throw BadRequestException("You can only download file")
        }

        return DownloadFileDto(
            mimeType = file.mimeType,
            fileName = URLEncoder.encode(file.fileName,"UTF-8"),
            byteArray =  externalFilesStorageService.getFile(file.fileExternalId)
        )
    }

    @PreAuthorize("hasPermission(#id, 'File', 'READ')")
    @Transactional(rollbackFor = [Exception::class])
    override fun update(id:Int, request: UpdateFileRequest): FileDto {
        throwIfUnauthorizedToMoveInFolder(request)
        throwIfNewParentIsNotDirectory(request)
        modifyRelatedFiles(id, request)

        return updateTemplate.update(id, request, FileDto::class.java)
    }

    private fun updateParentDirectories(file: Directory?, newAccessType: FileAccessType) {
        var currentFile = file

        while (currentFile != null) {
            currentFile.accessType = newAccessType
            fileRepository.save(currentFile)
            currentFile = currentFile.parent;
        }
    }

    private fun updateChildFiles(directory: Directory, newAccessType: FileAccessType) {
        val children = fileRepository.findAllByParent(directory);

        children.forEach { file ->
            file.accessType = newAccessType
            fileRepository.save(file)

            if (file is Directory) {
                updateChildFiles(file, newAccessType)
            }
        }
    }

    private fun modifyRelatedFiles(id: Int, request: UpdateFileRequest) {
        if (request.accessType != null) {
            val file = fileRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("$id")

            updateParentDirectories(file.parent, request.accessType)
            if (file is Directory) {
                updateChildFiles(file, request.accessType)
            }

        }
    }

    private fun getParent(parentId: Int?): Directory? {
        return if (parentId == null)
            null
        else
            fileRepository.findByIdOrNull(parentId) as? Directory
                ?: throw BadRequestException("Parent must be a directory")
    }

    private fun throwIfUnauthorizedToMoveInFolder(request: UpdateFileRequest) {
        if (request.parentId != null
            && filesPermissionEvaluator.hasPermission(null, targetId = request.parentId, permission = "UPLOAD_FILE")
        ) {
            throw UnauthorizedException("You don't have access to the folder ${request.parentId}")
        }
    }

    private fun throwIfNewParentIsNotDirectory(request: UpdateFileRequest) {
        if (request.parentId != null
            && fileRepository.findByIdOrNull(request.parentId)?.type != FileType.DIRECTORY) {
            throw BadRequestException("Parent must be a directory")
        }
    }

    private fun generateExternalId(): String = UUID.randomUUID().toString()
}