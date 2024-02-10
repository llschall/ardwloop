package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

public interface IArdwProgram {

    SerialData loop(KeyboardModel keyboardMdl, SerialData s);

    SerialData setup(SerialData s);

    default void post(SerialData p) {
        msg("post ignored");
    }

    String getName();

    char getId();

    int getRc();

    int getSc();

}
