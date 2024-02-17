package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.serial.jni.BackEntry;
import org.llschall.ardwloop.serial.jni.NativeEntry;

public class SerialTest1 {

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void setup() {

        MsgEntry back = new MsgEntry('T', 5, 7);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('T', entry.prg());
        Assertions.assertEquals(5, entry.rc());
        Assertions.assertEquals(7, entry.sc());

    }
}
