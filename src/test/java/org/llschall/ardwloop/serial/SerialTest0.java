package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.jni.NativeEntry;

public class SerialTest0 {

    @Test
    void ping() {
        NativeEntry entry = new NativeEntry();
        int ping = entry.ping();
        Assertions.assertEquals(2023, ping);
    }

    @Test
    void print() {
        NativeEntry entry = new NativeEntry();
        int ping = entry.print();
        Assertions.assertEquals(54, ping);
    }

    @Test
    void check() {
        NativeEntry entry = new NativeEntry();
        int check = entry.check(2022);
        Assertions.assertEquals(4044, check);
    }

}
