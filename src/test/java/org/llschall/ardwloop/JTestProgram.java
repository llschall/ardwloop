package org.llschall.ardwloop;

import org.llschall.ardwloop.value.SerialData;

public class JTestProgram implements IArdwProgram {

    @Override
    public SerialData ardwLoop(SerialData s) {

        return new SerialData(1, 1, 1, 1, 1);
    }

    @Override
    public SerialData ardwSetup(SerialData s) {
        return new SerialData(1, 1, 1, 1, 1);
    }
}
