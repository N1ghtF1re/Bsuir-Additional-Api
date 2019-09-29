package men.brakh.bsuirapi.bot

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito

class SessionTest {
    private var bot = Mockito.mock(AbstractBot::class.java)

    private val session = Session(1, bot)

    @Test
    fun extractTime() {
        val time = "Some string before (12:30-15:20)"

        assertEquals("12:31", session.extractTime(time))
    }

    @Test
    fun extractTime2() {
        val time = "Any time"

        assertEquals(null, session.extractTime(time))
    }
}