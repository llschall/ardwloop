package org.llschall.ardwloop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.llschall.ardwloop.value.LoopData

class ArdwloopKTest {

    @Test
    fun testProgramCanBeCreated() {
        val program = JTestProgram()

        val map = program.ardwSetup(LoopData())
        Assertions.assertEquals(1, map.a.v)
    }
}