package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.serial.jni.BackEntry;

public class ResetTest {

    @BeforeEach
    void setup() {
        BackEntry.setup(new ResetEntry());
    }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void check() {

        NativeEntry entry = new NativeEntry();
        entry.reset();
        char prg = entry.prg();
        Assertions.assertEquals('W', prg);
    }

    @Test
    void checkAgain() {

        NativeEntry entry = new NativeEntry();
        entry.reset();
        char prg = entry.prg();
        Assertions.assertEquals('W', prg);
    }

}
