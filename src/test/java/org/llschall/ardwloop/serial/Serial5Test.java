package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

import static org.llschall.ardwloop.serial.Serial.R;
import static org.llschall.ardwloop.serial.Serial.S;
import static org.llschall.ardwloop.serial.Serial.T;

public class Serial5Test {
    @BeforeEach
    void setup() {/* do nothing */ }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void testArray() {

        MsgEntry back = new MsgEntry('J', 77, 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_300);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());
        entry.loop();

        entry.importS("", 0, 'a', 0, 0, 0, 0, 0);
        back.addMsg(S + "001" + T, R + "7+64+++" + T);
        entry.loop();
        Assertions.assertEquals(7, entry.exportArray(0));
        Assertions.assertEquals(64, entry.exportArray(1));

    }
}
