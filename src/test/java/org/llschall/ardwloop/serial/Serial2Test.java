package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

public class Serial2Test {
    @BeforeEach
    void setup() {/* do nothing */ }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void test11() {

        MsgEntry back = new MsgEntry('J', 65, 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_1200);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());
        entry.loop();

        entry.importS("", 0, 'a', 0, 0, 54, 0, 0);
        back.addMsg(
                Serial.S + "001ax54+" + Serial.T,
                Serial.R + "av67+az68+" + Serial.T);

        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        entry.importS("", 0, 'a', 0, 0, 54, 0, 0);
        back.addMsg(
                Serial.S + "002ax54+" + Serial.T,
                Serial.R + "av67+az68+" + Serial.T);
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));
    }

    @Test
    void test12() {

        MsgEntry back = new MsgEntry('J', 54, 1, 2);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();

        entry.setup(IArdwConfig.BAUD_1200);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.rc());
        Assertions.assertEquals(2, entry.sc());
        entry.loop();

        entry.importS("", 0, 'a', 0, 0, 54, 0, 0);
        back.addMsg(
                Serial.S + "001ax54+" + Serial.T,
                Serial.R + "av67+az68+" + Serial.T);
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        entry.importS("", 0, 'a', 0, 0, 54, 0, 0);
        back.addMsg(
                Serial.S + "002ax54+" + Serial.T,
                Serial.R + "av67+az68+" + Serial.T);
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));
    }

    @Test
    void test99() {

        MsgEntry back = new MsgEntry('J', 85, 9, 9);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_1200);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(9, entry.sc());
        Assertions.assertEquals(9, entry.rc());
        entry.loop();

        back.addMsg(
                Serial.S + "001ax54+" + Serial.T,
                Serial.R + "az68+" + Serial.T);
        entry.importS("", 0, 'a', 0, 0, 54, 0, 0);
        entry.loop();
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        back.addMsg(
                Serial.S + "002" + Serial.T,
                Serial.R + "" + Serial.T);
        entry.importS("", 0, 'a', 0, 0, 0, 0, 0);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'z'));
    }
}
