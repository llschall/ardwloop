package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel;

public interface IArdwProgram {

    SerialData loop(KeyboardModel keyboardMdl, SerialData s);

}
