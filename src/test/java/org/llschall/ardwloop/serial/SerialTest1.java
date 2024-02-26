package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.serial.jni.BackEntry;

public class SerialTest1 {

    @BeforeEach
    void setup() {
        LocalOnly.get().skipOnGit();
    }

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
}
