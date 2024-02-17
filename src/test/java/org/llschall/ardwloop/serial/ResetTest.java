package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

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
        if (LocalOnly.get()) return;

        NativeEntry entry = new NativeEntry();
        entry.reset();
        char prg = entry.prg();
        Assertions.assertEquals('W', prg);
    }

    @Test
    void checkAgain() {
        if (LocalOnly.get()) return;

        NativeEntry entry = new NativeEntry();
        entry.reset();
        char prg = entry.prg();
        Assertions.assertEquals('W', prg);
    }

}
