package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

public class SerialTest2 {

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void test11() {

        MsgEntry back = new MsgEntry('J', 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());

        entry.importS('a', 0, 0, 54, 0, 0);
        back.addMsg(Serial.S + "000av+aw+ax54+ay+az+",
                Serial.R + "av67+aw+ax+ay+az68+");
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        entry.importS('a', 0, 0, 54, 0, 0);
        back.addMsg(Serial.S + "001av+aw+ax54+ay+az+",
                Serial.R + "av67+aw+ax+ay+az68+");
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));
    }

    @Test
    void test12() {

        MsgEntry back = new MsgEntry('J', 1, 2);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(2, entry.sc());
        Assertions.assertEquals(1, entry.rc());

        entry.importS('a', 0, 0, 54, 0, 0);
        back.addMsg(Serial.S + "000av+aw+ax54+ay+az+" + Msg.EMPTY_B,
                Serial.R + "av67+aw+ax+ay+az68+");
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        entry.importS('a', 0, 0, 54, 0, 0);
        back.addMsg(Serial.S + "001av+aw+ax54+ay+az+" + Msg.EMPTY_B,
                Serial.R + "av67+aw+ax+ay+az68+");
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));
    }

    @Test
    void test99() {

        MsgEntry back = new MsgEntry('J', 9, 9);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup();
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(9, entry.sc());
        Assertions.assertEquals(9, entry.rc());

        back.addMsg(Serial.S + "000av+aw+ax54+ay+az+" + Msg.EMPTY_B_I,
                Serial.R + "av+aw+ax+ay+az68+" + Msg.EMPTY_B_I);
        entry.importS('a', 0, 0, 54, 0, 0);
        entry.loop();
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        back.addMsg(Serial.S + "001" + Msg.EMPTY_MSG, Serial.R + Msg.EMPTY_MSG);
        entry.importS('a', 0, 0, 0, 0, 0);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'z'));
    }
}
