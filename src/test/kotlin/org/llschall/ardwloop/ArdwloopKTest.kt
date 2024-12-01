package org.llschall.ardwloop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.llschall.ardwloop.value.V
import org.llschall.ardwloop.value.ValueMap

class ArdwloopKTest {

    @Test
    fun testProgramCanBeCreated() {
        val program = JTestProgram()

        val map = program.ardwSetup(ValueMap())
        Assertions.assertEquals(1, map.a[V.v])
    }
}