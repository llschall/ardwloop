package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

public class Serial1Test {
    @BeforeEach
    void setup() {/* do nothing */ }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void setupTest() {

        MsgEntry back = new MsgEntry('T', 5, 7);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('T', entry.prg());
        Assertions.assertEquals(5, entry.rc());
        Assertions.assertEquals(7, entry.sc());
    }

    @Test
    void setupTest2() {

        MsgEntry back = new MsgEntry('T', 3, 2, 217, 19);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('T', entry.prg());
        Assertions.assertEquals(3, entry.rc());
        Assertions.assertEquals(2, entry.sc());
        Assertions.assertEquals(217, entry.delayRead());
        Assertions.assertEquals(19, entry.delayPost());
    }
}
