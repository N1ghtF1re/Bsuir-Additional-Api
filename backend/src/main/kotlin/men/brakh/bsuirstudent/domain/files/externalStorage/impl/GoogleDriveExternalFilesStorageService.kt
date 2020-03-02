package men.brakh.bsuirstudent.domain.files.externalStorage.impl

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.InputStreamContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import men.brakh.bsuirstudent.domain.files.externalStorage.ExternalFilesStorageService
import men.brakh.bsuirstudent.domain.files.externalStorage.UploadedFileDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

private typealias GoogleDriveFile = com.google.api.services.drive.model.File


// TODO: https://developers.google.com/identity/protocols/OAuth2ServiceAccount

@Service
open class GoogleDriveExternalFilesStorageService : ExternalFilesStorageService {
    private val jsonFactory = JacksonFactory.getDefaultInstance()
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
    }

    override fun getFile(fileExternalId: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        service.files().get(fileExternalId)
                .executeMediaAndDownloadTo(outputStream)


        return outputStream.toByteArray()
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

    override fun uploadFile(filename: String, inputStream: InputStream, contentType: String): UploadedFileDto {
        val fileMetadata = GoogleDriveFile()
        fileMetadata.name = filename

        val mediaContent =  InputStreamContent(contentType, inputStream)

        logger.info("Uploading new file $filename in directory root")

        val uploadedFile: GoogleDriveFile = service.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()

        return UploadedFileDto(
            id = uploadedFile.id,
            mimeType = uploadedFile.mimeType ?: contentType
        )
    }

    private val String.mimeType: String
        get() {
            return Files.probeContentType(Path.of(this)) ?: "text/plain"
        }
}
