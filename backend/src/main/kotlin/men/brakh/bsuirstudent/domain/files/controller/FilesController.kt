package men.brakh.bsuirstudent.domain.files.controller

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.domain.files.CreateDirectoryRequest
import men.brakh.bsuirstudent.domain.files.CreateLinkRequest
import men.brakh.bsuirstudent.domain.files.FileDto
import men.brakh.bsuirstudent.domain.files.UpdateFileRequest
import men.brakh.bsuirstudent.domain.files.service.FileService
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream


@RestController
@RequestMapping(path = ["/api/v2"])
class FilesController(
    private val fileService: FileService,
    @Value("\${spring.servlet.multipart.max-file-size}") private val maxSize: String
) {

    @GetMapping("/files/maxSize")
    @ResponseBody
    fun getMaxSize(): Map<String, String>{
        return mapOf("maxSize" to maxSize);
    }

    @PostMapping(path = ["/directories/root/file", "/directories/{parentId}/file"])
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun handleFileUpload(
        @PathVariable(name = "parentId", required = false) parentId: Int?,
        @RequestParam("file") file: MultipartFile
    ): FileDto {
        return fileService.uploadFile(
            filename = file.originalFilename ?: throw BadRequestException("Original filename is missed"),
            inputStream = file.inputStream,
            parentId = parentId,
            contentType = file.contentType ?: "plain/text"
        )
    }

    @PostMapping(path = ["/directories/root/directory", "/directories/{parentId}/directory"])
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun makeDirectory(
        @PathVariable(name = "parentId", required = false) parentId: Int?,
        @RequestBody request: CreateDirectoryRequest
    ): FileDto {
        return fileService.createDirectory(
            filename = request.fileName,
            parentId = parentId
        )
    }
    @PostMapping(path = ["/directories/root/link", "/directories/{parentId}/link"])
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun createLink(
        @PathVariable(name = "parentId", required = false) parentId: Int?,
        @RequestBody request: CreateLinkRequest
    ): FileDto {
        return fileService.uploadLink(
            filename = request.fileName,
            parentId = parentId,
            url = request.url
        )
    }


    @GetMapping("/files/{fileId}/download")
    fun download(@PathVariable(name = "fileId") fileId: Int): ResponseEntity<Resource> {
        val downloadFileDto = fileService.downloadFile(fileId)
        val resource = InputStreamResource(ByteArrayInputStream(downloadFileDto.byteArray))
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(downloadFileDto.mimeType))
            .header("Content-disposition", "attachment; filename=${downloadFileDto.fileName}")
            .body(resource)
    }

    @GetMapping("/files/{fileId}")
    @ResponseBody
    fun getFile(@PathVariable(name = "fileId") fileId: Int): FileDto {
        return fileService.getFile(fileId)
    }

    @PatchMapping("/files/{fileId}")
    @ResponseBody
    fun updateFile(@PathVariable(name = "fileId") fileId: Int, @RequestBody request: UpdateFileRequest): FileDto {
        return fileService.update(fileId, request)
    }


    @GetMapping(path = ["/directories/root/files", "/directories/{id}/files"])
    @ResponseBody
    fun getFilesInDirectory(@PathVariable(name = "id", required = false) parentId: Int?): List<FileDto> {
        return fileService.getAvailableFiles(parentId)
    }
}