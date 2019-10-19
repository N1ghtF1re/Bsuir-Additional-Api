package men.brakh.bsuirapi.app.file.service

import men.brakh.bsuirapi.app.file.model.*
import java.io.InputStream

interface FileService {
    /**
     * Upload file
     * @param filename filename
     * @param inputStream file's input stream
     * @param parentId parent directory's id. Null if need to upload file in root folder.
     */
    fun uploadFile(filename: String, inputStream: InputStream, parentId: Long? = null): File

    /**
     * Upload link
     * @param filename filename
     * @param url url
     * @param parentId parent directory's id. Null if need to upload file in root folder.
     */
    fun uploadLink(filename: String, url: String, parentId: Long? = null): Link

    /**
     * Create directory
     * @param filename directory name
     * @param parentId parent directory's id. Null if need to create folder in root folder.
     */
    fun createDirectory(filename: String, parentId: Long? = null): Directory

    /**
     * Get available for the current user files.
     * @param parentId parent directory's id. Null if the root folder.
     */
    fun getAvailableFiles(parentId: Long? = null): List<AbstractFile>

    /**
     * Get file
     * @param id file's id
     */
    fun getFile(id: Long): AbstractFile

    /**
     * File downloading
     * @param id id
     * @return mime type and byte array
     */
    fun downloadFile(id: Long): Pair<String, ByteArray>

    /**
     * Update file access type
     * @param id id
     * @param newAccessType new file access type
     */
    fun updateFileAccess(id: Long, newAccessType: FileAccessType): AbstractFile
}