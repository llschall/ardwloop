package org.llschall.ardwloop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.llschall.ardwloop.value.SerialData

class ArdwloopKTest {

    @Test
    fun testProgramCanBeCreated() {
        val program = JTestProgram()

        val map = program.ardwSetup(SerialData())
        Assertions.assertEquals(1, map.a.v)
    }
}