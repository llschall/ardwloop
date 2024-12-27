package org.llschall.ardwloop;

import org.llschall.ardwloop.value.ArdwData;

public class JTestProgram implements IArdwProgram {

    @Override
    public ArdwData ardwLoop(ArdwData s) {

        return new ArdwData(1, 1, 1, 1, 1);
    }

    @Override
    public ArdwData ardwSetup(ArdwData s) {
        return new ArdwData(1, 1, 1, 1, 1);
    }
}
