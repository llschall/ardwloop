package org.llschall.ardwloop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ArdwloopKTest {

    @Test
    fun testProgramCanBeCreated() {
        val program = JTestProgram()
        val rc = program.rc
        Assertions.assertEquals(1, rc)
    }
}