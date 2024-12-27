package org.llschall.ardwloop;

import org.llschall.ardwloop.value.LoopData;

public class JTestProgram implements IArdwProgram {

    @Override
    public LoopData ardwLoop(LoopData s) {

        return new LoopData(1, 1, 1, 1, 1);
    }

    @Override
    public LoopData ardwSetup(LoopData s) {
        return new LoopData(1, 1, 1, 1, 1);
    }
}
