package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.NativeEntry;

public class SerialTest0 {

    @Test
    void ping() {
        if (LocalOnly.get()) return;

        NativeEntry entry = new NativeEntry();
        int ping = entry.ping();
        Assertions.assertEquals(2023, ping);
    }

    @Test
    void print() {
        if (LocalOnly.get()) return;

        NativeEntry entry = new NativeEntry();
        int ping = entry.print();
        Assertions.assertEquals(54, ping);
    }

    @Test
    void check() {
        if (LocalOnly.get()) return;

        NativeEntry entry = new NativeEntry();
        int check = entry.check(2022);
        Assertions.assertEquals(4044, check);
    }

}
