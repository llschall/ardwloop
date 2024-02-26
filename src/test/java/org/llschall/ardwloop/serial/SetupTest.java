package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.NativeEntry;

public class SetupTest {

    @Test
    public void testSetup() {
        System.out.println("#################");
        System.out.println("## Setup Test ###");
        System.out.println("#################");

        Assertions.assertEquals(1, 1);
    }

    @Test
    public void testProperty() {
        String property = System.getProperty("test.setup");
        Assertions.assertEquals("Allschwil", property);
    }

    @Test
    public void testJni() {
        LocalOnly.get().skipOnGitHub();
        NativeEntry entry = new NativeEntry(true);
        int ping = entry.ping(2023);
        Assertions.assertEquals(2023, ping);
        int pong = entry.pong(2024);
        Assertions.assertEquals(2024, pong);
    }
}
