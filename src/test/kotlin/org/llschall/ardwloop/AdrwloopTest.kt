package org.llschall.ardwloop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AdrwloopTest {

    @Test
    fun testProgramCanBeCreated() {
        val program = JTestProgram()
        val rc = program.rc
        Assertions.assertEquals(1, rc)
    }
}