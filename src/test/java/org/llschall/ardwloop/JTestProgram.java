package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SetupData;

public class JTestProgram implements IArdwProgram {

    @Override
    public SerialData ardwLoop(SerialData s) {
        return new SerialData(0, 1, 1, 1, 1, 1);
    }

    @Override
    public SetupData ardwSetup(SetupData s) {
        return new SetupData(new SerialData(0, 1, 1, 1, 1, 1));
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
    public int getReadDelayMs() {
        return 0;
    }

    @Override
    public int getPostDelayMs() {
        return 0;
    }
}
