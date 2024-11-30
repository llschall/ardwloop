package org.llschall.ardwloop;

import org.llschall.ardwloop.value.ValueMap;

public class JTestProgram implements IArdwProgram {

    @Override
    public ValueMap ardwLoop(ValueMap s) {
        return new ValueMap(1, 1, 1, 1, 1);
    }

    @Override
    public ValueMap ardwSetup(ValueMap s) {
        return new ValueMap(1, 1, 1, 1, 1);
    }
}
