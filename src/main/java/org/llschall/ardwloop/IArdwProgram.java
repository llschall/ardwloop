package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

public interface IArdwProgram {

    SerialData loop(SerialData s);

    SerialData setup(SerialData s);

    default void post(SerialData p) {
        msg("post ignored");
    }

    String getName();

    char getId();

    int getRc();

    int getSc();

}
