package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.LoopData;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SetupData;

public class JTestProgram implements IArdwProgram {

    @Override
    public LoopData ardwLoop(LoopData s) {
        return new LoopData(new SerialData(0, 1, 1, 1, 1, 1));
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

}
