package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.serial.jni.BackEntry;

public class ResetTest {

    @BeforeEach
    void setup() {
        LocalOnly.get().skipOnGitHub();
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
        Assertions.assertEquals(5, entry.rc());
        Assertions.assertEquals(4, entry.sc());
        Assertions.assertEquals(7, entry.delayRead());
        Assertions.assertEquals(9, entry.delayPost());
    }

    @Test
    void checkAgain() {

        NativeEntry entry = new NativeEntry();
        entry.reset();
        char prg = entry.prg();
        Assertions.assertEquals('W', prg);
    }

}
