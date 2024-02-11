package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;

public class JTestProgram implements IArdwProgram {

    @Override
    public SerialData loop(SerialData s) {
        return new SerialData(0, 1, 1, 1, 1, 1);
    }

    @Override
    public SerialData setup(SerialData s) {
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
}
