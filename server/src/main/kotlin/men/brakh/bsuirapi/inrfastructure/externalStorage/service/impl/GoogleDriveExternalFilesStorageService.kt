package men.brakh.bsuirapi.inrfastructure.externalStorage.service.impl

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.InputStreamContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import men.brakh.bsuirapi.inrfastructure.externalStorage.service.ExternalFilesStorageService
import org.slf4j.LoggerFactory
import java.io.*
import javax.activation.MimetypesFileTypeMap

private typealias GoogleDriveFile = com.google.api.services.drive.model.File

class GoogleDriveExternalFilesStorageService : ExternalFilesStorageService {
    private val jsonFactory = JacksonFactory.getDefaultInstance()
    private val tokensDirectoryPath = "tokens"
    private val scopes = DriveScopes.all()
    private val credentialsFileNamePath = "/gd-credentials.json"
    private val service: Drive

    private val logger = LoggerFactory.getLogger(GoogleDriveExternalFilesStorageService::class.java)

    private val String.mimeType: String
        get() {
            val fileTypeMap = MimetypesFileTypeMap()
            return fileTypeMap.getContentType(this)
        }



    init {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val service = Drive.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
                .setApplicationName("BSUIR ADDITIONAL API")
                .build()
        this.service = service
    }

    private fun getResource(path: String): InputStream {
        return GoogleDriveExternalFilesStorageService::class.java.getResourceAsStream(path)
                ?: throw FileNotFoundException("Resource not found: $path")
    }

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {
        val credentials = getResource(credentialsFileNamePath)

        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(credentials))

        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes
        )
                .setDataStoreFactory(FileDataStoreFactory(File(tokensDirectoryPath)))
                .setAccessType("offline")
                .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    override fun getFile(fileId: String): OutputStream {
        val outputStream = ByteArrayOutputStream()
        service.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream)

        return outputStream
    }

    override fun makeDir(directoryName: String, parentId: String?): String {
        val fileMetadata = GoogleDriveFile()
        fileMetadata.name = directoryName

        parentId?.let { fileMetadata.parents = listOf(it) }

        fileMetadata.mimeType = "application/vnd.google-apps.folder"

        logger.info("Creation new folder $directoryName in directory $parentId")

        val uploadedFile: GoogleDriveFile = service.files().create(fileMetadata)
                .execute()

        return uploadedFile.id
    }

    override fun uploadFile(filename: String, inputStream: InputStream, parentId: String?): String {
        val fileMetadata = GoogleDriveFile()
        fileMetadata.name = filename
        parentId?.let { fileMetadata.parents = listOf(it) }

        val mediaContent =  InputStreamContent(filename.mimeType, inputStream)

        logger.info("Uploading new file $filename in directory $parentId")

        val uploadedFile: GoogleDriveFile = service.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()

        return uploadedFile.id
    }
}