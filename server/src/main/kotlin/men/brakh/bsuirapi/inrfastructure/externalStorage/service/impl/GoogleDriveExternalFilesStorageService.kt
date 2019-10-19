package men.brakh.bsuirapi.inrfastructure.externalStorage.service.impl

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import men.brakh.bsuirapi.inrfastructure.externalStorage.service.ExternalFilesStorageService
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import javax.activation.MimetypesFileTypeMap

private typealias GoogleDriveFile = com.google.api.services.drive.model.File


// TODO: https://developers.google.com/identity/protocols/OAuth2ServiceAccount
class GoogleDriveExternalFilesStorageService : ExternalFilesStorageService {
    private val jsonFactory = JacksonFactory.getDefaultInstance()
    private val tokensDirectoryPath = "/home/alex/Documents/Java_Projects/bsuir-additional-api/server/tokens"
    private val scopes = DriveScopes.all()
    private val credentialsFileNamePath = "/gd-credentials.json"
    private val service: Drive

    private val logger = LoggerFactory.getLogger(GoogleDriveExternalFilesStorageService::class.java)


    init {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val service = Drive.Builder(httpTransport, jsonFactory, getCredentials())
                .setApplicationName("BSUI R ADDITIONAL API")
                .build()
        this.service = service
    }

    private fun getResource(path: String): InputStream {
        return GoogleDriveExternalFilesStorageService::class.java.getResourceAsStream(path)
                ?: throw FileNotFoundException("Resource not found: $path")
    }

    private fun getCredentials(): Credential {
        return GoogleCredential.fromStream(
                getResource(credentialsFileNamePath)
        ).createScoped(scopes)

        /*val credentials = getResource(credentialsFileNamePath)

        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(credentials))

        val flow = GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes
        )
                .setDataStoreFactory(FileDataStoreFactory(File(tokensDirectoryPath)))
                .setAccessType("offline")
                .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")*/
    }

    override fun getFile(fileExternalId: String): Pair<String, ByteArray> {
        val outputStream = ByteArrayOutputStream()
        service.files().get(fileExternalId)
                .executeMediaAndDownloadTo(outputStream)

        val mimeType = service.files().get(fileExternalId)
                .execute()
                .mimeType

        return mimeType to outputStream.toByteArray()
    }

    override fun makeDir(directoryName: String, parentExternalId: String?): String {
        val fileMetadata = GoogleDriveFile()
        fileMetadata.name = directoryName

        parentExternalId?.let { fileMetadata.parents = listOf(it) }

        fileMetadata.mimeType = "application/vnd.google-apps.folder"

        logger.info("Creation new folder $directoryName in directory $parentExternalId")

        val uploadedFile: GoogleDriveFile = service.files().create(fileMetadata)
                .execute()

        return uploadedFile.id
    }

    override fun uploadFile(filename: String, inputStream: InputStream, parentExternalId: String?): String {
        val fileMetadata = GoogleDriveFile()
        fileMetadata.name = filename
        parentExternalId?.let { fileMetadata.parents = listOf(it) }

        val mediaContent =  InputStreamContent(filename.mimeType, inputStream)

        logger.info("Uploading new file $filename in directory $parentExternalId")

        val uploadedFile: GoogleDriveFile = service.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()

        return uploadedFile.id
    }

    private val String.mimeType: String
        get() {
            val fileTypeMap = MimetypesFileTypeMap()
            return fileTypeMap.getContentType(this) ?: "text/plain"
        }
}
