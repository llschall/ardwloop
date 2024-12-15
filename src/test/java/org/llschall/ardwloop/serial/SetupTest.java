package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.jni.NativeEntry;

import java.util.ArrayList;
import java.util.SequencedCollection;

public class SetupTest {

    @Test
    public void testJava() {
        // SequencedCollection require at least Java 21
        SequencedCollection<String> collection = new ArrayList<>();
        collection.addFirst("test");
        Assertions.assertEquals("test", collection.getFirst());
    }

    @Test
    public void testProperty() {
        String property = System.getProperty("test.setup");
        Assertions.assertEquals("Allschwil", property);
    }

    @Test
    public void testJni() {
        NativeEntry entry = new NativeEntry(true);
        int ping = entry.ping(2023);
        Assertions.assertEquals(2023, ping);
        int pong = entry.pong(2024);
        Assertions.assertEquals(2024, pong);
    }
}
