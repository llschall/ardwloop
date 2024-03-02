package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

public interface IArdwProgram {

    SerialData loop(SerialData s);

    SerialData setup(SerialData s);

    default void post(SerialData p) {
        msg("post ignored");
    }

    default char getId() {
        return 'a';
    }

    int getRc();

    int getSc();

    int getRead();

    int getPost();

}
