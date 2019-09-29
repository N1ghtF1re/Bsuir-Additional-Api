package men.brakh.bsuirapi.bot

import org.junit.Assert.*
import org.junit.Test

class SessionStateTest {

    @Test
    fun next() {
        val sessionState = SessionState.SELECT_BUILDING
        assertEquals(sessionState.next(), SessionState.SELECT_FLOOR)
    }

    @Test
    fun next1() {
        val sessionState = SessionState.SELECT_FLOOR
        assertEquals(sessionState.next(), SessionState.SELECT_DATE)
    }

    @Test
    fun next2() {
        val sessionState = SessionState.SELECT_DATE
        assertEquals(sessionState.next(), SessionState.SELECT_TIME)
    }

    @Test
    fun next3() {
        val sessionState = SessionState.SELECT_TIME
        assertEquals(sessionState.next(), SessionState.SELECT_BUILDING)
    }
}