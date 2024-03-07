package org.llschall.ardwloop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArdwloopJTest {

    @Test
    public void testApiFromJava() {

        JTestProgram program = new JTestProgram();
        char id = program.getProgramId();

        Assertions.assertEquals('a', id);
    }
}
