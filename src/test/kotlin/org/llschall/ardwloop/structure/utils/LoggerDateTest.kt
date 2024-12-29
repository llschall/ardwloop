package org.llschall.ardwloop.structure.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoggerDateTest {

    @Test
    fun testFormat2() {
        val date = LoggerDate()
        assertEquals("00", date.addZero(2, 0))
        assertEquals("02", date.addZero(2, 2))
        assertEquals("10", date.addZero(2, 10))
        assertEquals("11", date.addZero(2, 11))
    }

    @Test
    fun testFormat3() {
        val date = LoggerDate()
        assertEquals("000", date.addZero(3, 0))
        assertEquals("002", date.addZero(3, 2))
        assertEquals("010", date.addZero(3, 10))
        assertEquals("011", date.addZero(3, 11))
        assertEquals("100", date.addZero(3, 100))
        assertEquals("123", date.addZero(3, 123))
    }
}