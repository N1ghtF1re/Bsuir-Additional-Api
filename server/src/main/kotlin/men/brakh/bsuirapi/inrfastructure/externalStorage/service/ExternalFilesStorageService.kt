package men.brakh.bsuirapi.inrfastructure.externalStorage.service

import java.io.InputStream

/**
 * Service for storing files in external storage and getting this files from external storage
 */
interface ExternalFilesStorageService {
    /**
     * Get file by unique id in external filestorage service
     * @return mime type and output stream
     */
    fun getFile(fileExternalId: String): Pair<String, ByteArray>

    /**
     * Upload file
     *
     * @param filename Name of file (with extention)
     * @param inputStream inputstream with file's content
     * @param parentExternalId id of parent folder (null if need to store file in the main directory
     * @return unique fileid in external filestorage service
     */
    fun uploadFile(filename: String, inputStream: InputStream, parentExternalId: String? = null): String

    /**
     * Make dir
     *
     * @directoryName name of directory
     * @param parentExternalId id of parent folder (null if need to store file in the main directory
     * @return unique direcotry id in external filestirage service
     */
    fun makeDir(directoryName: String, parentExternalId: String? = null): String
}