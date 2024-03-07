package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;

public class JTestProgram implements IArdwProgram {

    @Override
    public SerialData ardwLoop(SerialData s) {
        return new SerialData(0, 1, 1, 1, 1, 1);
    }

    @Override
    public SerialData ardwSetup(SerialData s) {
        return new SerialData(0, 1, 1, 1, 1, 1);
    }

    @Override
    public int getRc() {
        return 1;
    }

    @Override
    public int getSc() {
        return 1;
    }

    @Override
    public int getRead() {
        return 0;
    }

    @Override
    public int getPost() {
        return 0;
    }
}
