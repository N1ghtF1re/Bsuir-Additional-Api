package men.brakh.bsuirapi.app.file.service.impl

import io.mockk.*
import men.brakh.bsuirapi.AccessDeniedException
import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.app.file.model.*
import men.brakh.bsuirapi.app.file.service.FileService
import men.brakh.bsuirapi.app.users.service.UserService
import men.brakh.bsuirapi.inrfastructure.authorization.AuthenticationManager
import men.brakh.bsuirapi.inrfastructure.externalStorage.service.ExternalFilesStorageService
import men.brakh.bsuirapi.repository.FileRepository
import men.brakh.bsuirapicore.model.data.StudyInfo
import men.brakh.bsuirapicore.model.data.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.InputStream
import kotlin.test.assertTrue

class FileServiceImplTest {

    private lateinit var fileService: FileService
    private lateinit var externalFileService: ExternalFilesStorageService
    private lateinit var filesRepository: FileRepository
    private val username = "testUsername"
    private lateinit var user: User

    private lateinit var mainParent: Directory

    private val mainParentId = "ASSDSAD"


    @Before
    fun setUp() {
        externalFileService = mockk()
        filesRepository = mockk()

        fileService = spyk(
                objToCopy = FileServiceImpl(externalFileService, filesRepository),
                recordPrivateCalls = true
        )

        val education = mockk<StudyInfo>()

        every { education.group } returns "751006"


        user = mockk()

        every { user.id } returns 1
        every { user.education } returns education


        mockkObject(AuthenticationManager)
        every { AuthenticationManager.getCurrentUserLogin() } returns username



        val userService = mockk<UserService> {
            every { getAuthenticatedUser() } returns user
        }

        mainParent = mockk()

        every { filesRepository.find(fileType = FileType.DIRECTORY,
                parent = null, fileName = "751006") } returns listOf(mainParent)

        every { mainParent.fileExternalId } returns mainParentId

        mockkObject(Config)
        every { Config.userService } returns userService
        every { Config.authenticationManager } returns AuthenticationManager
        every { filesRepository.find(fileName = any(), parent = any()) } returns listOf()

    }

    @Test
    fun uploadFile() {
        val filename = "test.txt"
        val fileId = "testId"
        val inputStream = mockk<InputStream>()
        val file = mockk<File>()

        every { externalFileService.uploadFile(any(), any(), any()) } returns fileId
        every { filesRepository.add(any(), any()) } returns file


        fileService.uploadFile(filename, inputStream)


        verify(exactly = 1) {
            externalFileService.uploadFile(filename, inputStream, mainParentId)
        }

        verify(exactly = 1) {
            filesRepository.add(
                    File(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = fileId,
                            userId = 1
                    ),
                    null
            )
        }
    }

    @Test
    fun uploadFileWithParent() {
        val filename = "test.txt"
        val fileId = "testId"
        val dirId = "testDirId"
        val inputStream = mockk<InputStream>()
        val file = mockk<File>()

        every { externalFileService.uploadFile(any(), any(), any()) } returns fileId
        every { filesRepository.add(any(), any()) } returns file

        val directory = mockk<Directory>()
        every { directory.fileExternalId } returns dirId
        every { filesRepository.findById(255) } returns directory

        fileService.uploadFile(filename, inputStream, 255)

        verify(exactly = 1) {
            filesRepository.findById(255)
        }

        verify(exactly = 1) {
            externalFileService.uploadFile(filename, inputStream, dirId)
        }

        verify(exactly = 1) {
            filesRepository.add(
                    File(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = fileId,
                            userId = 1
                    ),
                    255
            )
        }
    }

    @Test
    fun uploadLink() {

        val filename = "test.link"
        val link = "https://brakh.men"
        val file = mockk<Link>()

        every { filesRepository.add(any(), any()) } returns file
        every { fileService["generateExternalId"]() } returns "KEK"


        fileService.uploadLink(filename, link)




        verify(exactly = 1) {
            filesRepository.add(
                    Link(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = "KEK",
                            userId = 1,
                            url = link
                    ),
                    null
            )
        }


    }

    @Test
    fun uploadLinkWithParent() {

        val filename = "test.link"
        val link = "https://brakh.men"
        val file = mockk<Link>()

        every { filesRepository.add(any(), any()) } returns file
        every { fileService["generateExternalId"]() } returns "KEK"


        fileService.uploadLink(filename, link, 255)




        verify(exactly = 1) {
            filesRepository.add(
                    Link(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = "KEK",
                            userId = 1,
                            url = link
                    ),
                    255
            )
        }


    }


    @Test
    fun updateFileAccess() {
        val file = mockk<AbstractFile>()
        every { file.userId } returns 1
        every { filesRepository.findById(1) } returns file

        val copiedFile = mockk<AbstractFile>()
        every { file.copyWithAccessType(FileAccessType.STREAM) } returns copiedFile
        every { filesRepository.update(copiedFile) } returns copiedFile
        every { filesRepository.getParentId(any()) } returns null

        fileService.updateFileAccess(1, FileAccessType.STREAM)

        verify(exactly = 1) {
            filesRepository.update(copiedFile)
        }
    }

    @Test
    fun updateFileAccessWithNoRights() {
        val file = mockk<AbstractFile>()
        every { file.userId } returns 2
        every { filesRepository.findById(1) } returns file


        val result = try {
            fileService.updateFileAccess(1, FileAccessType.STREAM)
            false
        } catch (e: AccessDeniedException) {
            true
        }

        assertTrue { result }

        verify(exactly = 0) {
            filesRepository.update(any())
        }
    }

    @Test
    fun createDirectory() {
        val filename = "testDir"
        val fileId = "testId"
        val file = mockk<Directory>()

        every { externalFileService.makeDir(any(), any()) } returns fileId
        every { filesRepository.add(any(), any()) } returns file


        fileService.createDirectory(filename)


        verify(exactly = 1) {
            externalFileService.makeDir(filename, mainParentId)
        }

        verify(exactly = 1) {
            filesRepository.add(
                    Directory(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = fileId,
                            userId = 1,
                            files = listOf()
                    ),
                    null
            )
        }
    }

    @Test
    fun createDirectoryWithParent() {
        val filename = "testDir"
        val fileId = "testId"
        val dirId = "testDirId"
        val file = mockk<Directory>()

        every { externalFileService.makeDir(any(), any()) } returns fileId
        every { filesRepository.add(any(), any()) } returns file

        val parentDirectory = mockk<Directory>()
        every { parentDirectory.fileExternalId } returns dirId
        every { filesRepository.findById(255) } returns parentDirectory

        fileService.createDirectory(filename, 255)

        verify(exactly = 1) {
            filesRepository.findById(255)
        }

        verify(exactly = 1) {
            externalFileService.makeDir(filename, dirId)
        }

        verify(exactly = 1) {
            filesRepository.add(
                    Directory(
                            fileName = filename,
                            accessType = FileAccessType.GROUP,
                            ownedGroup = "751006",
                            fileExternalId = fileId,
                            userId = 1,
                            files = listOf()
                    ),
                    255
            )
        }
    }

    @Test
    fun getAvailableFiles() {
        every { filesRepository.find(
                ownerGroup = "751006",
                accessType = FileAccessType.GROUP,
                parent = null
        )} returns listOf()

        every { filesRepository.find(
                ownedGroupLike = "75100%",
                accessType = FileAccessType.STREAM,
                parent = null
        )} returns listOf()

        fileService.getAvailableFiles()

        verify(exactly = 2) {
            filesRepository.find(any(), any(), any(), any(), any(), any(), any(), any())
        }
    }

    @Test
    fun getAvailableFilesWithParent() {
        every { filesRepository.find(
                ownerGroup = "751006",
                accessType = FileAccessType.GROUP,
                parent = 1
        )} returns listOf()

        every { filesRepository.find(
                ownedGroupLike = "75100%",
                accessType = FileAccessType.STREAM,
                parent = 1
        )} returns listOf()

        fileService.getAvailableFiles(1)

        verify(exactly = 2) {
            filesRepository.find(parent = 1, ownedGroupLike = any(), ownerGroup = any(), accessType = any())
        }
    }


    @Test
    fun getFile() {
        val file = mockk<AbstractFile>()

        every { file.accessType } returns FileAccessType.GROUP
        every { file.ownedGroup } returns "751006"

        every { filesRepository.findById(1) } returns file
        fileService.getFile(1)

        verify(exactly = 1) {
            filesRepository.findById(1)
        }
    }

    @Test(expected = AccessDeniedException::class)
    fun getFileAccessDenied() {
        val file = mockk<AbstractFile>()

        every { file.accessType } returns FileAccessType.GROUP
        every { file.ownedGroup } returns "751009"
        every { file.fileName } returns "kek"

        every { filesRepository.findById(1) } returns file

        fileService.getFile(1)
    }


    @Test
    fun downloadFile() {
        val fileId = "aa"
        val arr = ByteArray(1)
        every { externalFileService.getFile(any()) } returns ("" to arr)

        val file = mockk<File>()
        every { file.accessType } returns FileAccessType.STREAM
        every { file.ownedGroup } returns "751007"
        every { file.fileName } returns "kek"
        every { file.fileType } returns FileType.FILE

        every { file.fileExternalId } returns fileId

        every { filesRepository.findById(1) } returns file

        fileService.downloadFile(1)

        verify(exactly = 1) {
            filesRepository.findById(1)
        }
        verify(exactly = 1) {
            externalFileService.getFile(fileId)
        }
    }

    @Test
    fun downloadDirectory() {
        val fileId = "aa"
        val arr = ByteArray(1)
        every { externalFileService.getFile(any()) } returns ("" to arr)

        val file = mockk<File>()
        every { file.accessType } returns FileAccessType.STREAM
        every { file.ownedGroup } returns "751007"
        every { file.fileName } returns "kek"
        every { file.fileType } returns FileType.DIRECTORY

        every { file.fileExternalId } returns fileId

        every { filesRepository.findById(1) } returns file

        assertTrue {
            try {
                fileService.downloadFile(1)
                false
            } catch (e: IllegalArgumentException) {
                true
            }
        }

        verify(exactly = 1) {
            filesRepository.findById(1)
        }
        verify(exactly = 0) {
            externalFileService.getFile(fileId)
        }
    }

    @Test
    fun downloadLink() {
        val fileId = "aa"
        val arr = ByteArray(1)
        every { externalFileService.getFile(any()) } returns ("" to arr)

        val file = mockk<File>()
        every { file.accessType } returns FileAccessType.STREAM
        every { file.ownedGroup } returns "751007"
        every { file.fileName } returns "kek"
        every { file.fileType } returns FileType.LINK

        every { file.fileExternalId } returns fileId

        every { filesRepository.findById(1) } returns file

        assertTrue {
            try {
                fileService.downloadFile(1)
                false
            } catch (e: IllegalArgumentException) {
                true
            }
        }

        verify(exactly = 1) {
            filesRepository.findById(1)
        }
        verify(exactly = 0) {
            externalFileService.getFile(fileId)
        }
    }

    @Test
    fun downloadFileWithAccessDenied() {
        val fileId = "aa"
        val arr = ByteArray(1)
        every { externalFileService.getFile(any()) } returns ("" to arr)

        val file = mockk<File>()
        every { file.accessType } returns FileAccessType.GROUP
        every { file.ownedGroup } returns "751007"
        every { file.fileName } returns "kek"
        every { file.fileType } returns FileType.FILE

        every { file.fileExternalId } returns fileId

        every { filesRepository.findById(1) } returns file

        assertTrue {
            try {
                fileService.downloadFile(1)
                false
            } catch (e: AccessDeniedException) {
                true
            }
        }

        verify(exactly = 1) {
            filesRepository.findById(1)
        }
        verify(exactly = 0) {
            externalFileService.getFile(fileId)
        }
    }


    @After
    fun dispose() {
        unmockkAll()
    }
}