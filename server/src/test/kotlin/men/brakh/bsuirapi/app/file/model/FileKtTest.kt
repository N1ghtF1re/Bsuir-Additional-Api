package men.brakh.bsuirapi.app.file.model

import io.mockk.every
import io.mockk.mockk
import men.brakh.bsuirapicore.model.data.StudyInfo
import men.brakh.bsuirapicore.model.data.User
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileKtTest {
    var user: User = mockk<User>()

    @Before
    fun setUp() {
        val education = mockk<StudyInfo>()

        every { education.group }.returns("751006")
        every { user.education }.returns(education)
    }

    @Test
    fun isAvailableForGroupTest() {
        val fileAvailableForGroup = mockk<AbstractFile>()
        every { fileAvailableForGroup.ownedGroup }.returns("751006")
        every { fileAvailableForGroup.accessType }.returns(FileAccessType.GROUP)


        assertTrue {
            fileAvailableForGroup.isAvailable(user)
        }

    }

    @Test
    fun isUnavailableForGroupAvailableTest() {
        val fileAvailableForGroup = mockk<AbstractFile>()
        every { fileAvailableForGroup.ownedGroup }.returns("751007")
        every { fileAvailableForGroup.accessType }.returns(FileAccessType.GROUP)


        assertFalse {
            fileAvailableForGroup.isAvailable(user)
        }
    }

    @Test
    fun isAvailableForStreamTest() {
        val fileAvailableForGroup = mockk<AbstractFile>()
        every { fileAvailableForGroup.ownedGroup }.returns("751007")
        every { fileAvailableForGroup.accessType }.returns(FileAccessType.STREAM)


        assertTrue {
            fileAvailableForGroup.isAvailable(user)
        }
    }

    @Test
    fun isUnailableForStreamTest() {
        val fileAvailableForGroup = mockk<AbstractFile>()
        every { fileAvailableForGroup.ownedGroup }.returns("851006")
        every { fileAvailableForGroup.accessType }.returns(FileAccessType.STREAM)


        assertFalse() {
            fileAvailableForGroup.isAvailable(user)
        }
    }
}