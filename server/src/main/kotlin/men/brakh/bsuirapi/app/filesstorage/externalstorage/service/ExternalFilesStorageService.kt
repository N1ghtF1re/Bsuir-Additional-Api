package men.brakh.bsuirapi.app.filesstorage.externalstorage.service

import java.io.InputStream
import java.io.OutputStream


interface ExternalFilesStorageService {
    /**
     * Get file by unique id in external filestorage service
     */
    fun getFile(fileId: String): OutputStream


    /**
     * Upload file
     *
     * @param filename Name of file (with extention)
     * @param inputStream inputstream with file's content
     * @param parentId id of parent folder (null if need to store file in the main directory
     * @return unique fileid in external filestorage service
     */
    fun uploadFile(filename: String, inputStream: InputStream, parentId: String? = null): String

    /**
     * Make dir
     *
     * @directoryName name of directory
     * @param parentId id of parent folder (null if need to store file in the main directory
     * @return unique direcotry id in external filestirage service
     */
    fun makeDir(directoryName: String, parentId: String? = null): String
}