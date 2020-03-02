package men.brakh.bsuirstudent.domain.files.service

import men.brakh.bsuirstudent.domain.files.DownloadFileDto
import men.brakh.bsuirstudent.domain.files.FileDto
import men.brakh.bsuirstudent.domain.files.UpdateFileRequest
import java.io.InputStream

interface FileService {
    /**
     * Upload file
     * @param filename filename
     * @param inputStream file's input stream
     * @param parentId parent directory's id. Null if need to upload file in root folder.
     */
    fun uploadFile(filename: String, inputStream: InputStream, parentId: Int? = null, contentType: String): FileDto

    /**
     * Upload link
     * @param filename filename
     * @param url url
     * @param parentId parent directory's id. Null if need to upload file in root folder.
     */
    fun uploadLink(filename: String, url: String, parentId: Int? = null): FileDto

    /**
     * Create directory
     * @param filename directory name
     * @param parentId parent directory's id. Null if need to create folder in root folder.
     */
    fun createDirectory(filename: String, parentId: Int? = null): FileDto

    /**
     * Get available for the current user files.
     * @param parentId parent directory's id. Null if the root folder.
     */
    fun getAvailableFiles(parentId: Int? = null): List<FileDto>

    /**
     * Get file
     * @param id file's id
     */
    fun getFile(id: Int): FileDto

    /**
     * File downloading
     * @param id id
     * @return mime type and byte array
     */
    fun downloadFile(id: Int): DownloadFileDto

    fun update(id: Int, request: UpdateFileRequest): FileDto
}