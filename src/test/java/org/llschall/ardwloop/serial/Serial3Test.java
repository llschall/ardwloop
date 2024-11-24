package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

import static org.llschall.ardwloop.serial.Serial.*;

public class Serial3Test {
    @BeforeEach
    void setup() {/* do nothing */ }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void test11() {

        MsgEntry back = new MsgEntry('J', 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_300);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());

        entry.importS('a', 0, 0, 54, 0, 0);
        back.addMsg(S + "001av+aw+ax54+ay+az+", R + "av67+aw+ax+ay+az68+");
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        back.addMsg(S + "002" + Msg.EMPTY_A, "");
        back.addMsg(P + "000" + Msg.EMPTY_A, R + Msg.EMPTY_A);
        entry.importS('a', 0, 0, 0, 0, 0);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'v'));
        Assertions.assertEquals(0, entry.exportR('a', 'z'));

        entry.importS('a', 0, 0, 57, 0, 0);
        back.addMsg(S + "003av+aw+ax57+ay+az+", R + "av+aw+ax+ay+az62+");
        entry.loop();
        Assertions.assertEquals(62, entry.exportR('a', 'z'));

        back.addMsg(S + "004" + Msg.EMPTY_A, "");

        for (int i = 1; i < 10; i++) {
            back.addMsg(P + "00" + i + Msg.EMPTY_A, "");
        }
        for (int i = 10; i < 100; i++) {
            back.addMsg(P + "0" + i + Msg.EMPTY_A, "");
        }
        for (int i = 100; i < 1000; i++) {
            back.addMsg(P_ + i + Msg.EMPTY_A, "");
        }
        back.addMsg(P + "000" + Msg.EMPTY_A, "");
        back.addMsg(P + "001" + Msg.EMPTY_A, "");
        back.addMsg(P + "002" + Msg.EMPTY_A, R + Msg.EMPTY_A);

        entry.importS('a', 0, 0, 0, 0, 0);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'v'));
        Assertions.assertEquals(0, entry.exportR('a', 'z'));
    }

    @Test
    void test1000() {

        MsgEntry back = new MsgEntry('J', 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_300);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());

        back.addMsg(S + "001" + Msg.EMPTY_A, "");

        for (int i = 0; i < 10; i++) {
            back.addMsg(P + "00" + i + Msg.EMPTY_A, "");
        }
        for (int i = 10; i < 100; i++) {
            back.addMsg(P + "0" + i + Msg.EMPTY_A, "");
        }
        for (int i = 100; i < 998; i++) {
            back.addMsg(P_ + i + Msg.EMPTY_A, "");
        }
        back.addMsg(P + "998" + Msg.EMPTY_A, "");
        back.addMsg(P + "999" + Msg.EMPTY_A, "");
        back.addMsg(P + "000" + Msg.EMPTY_A, "");
        back.addMsg(P + "001" + Msg.EMPTY_A, "");
        back.addMsg(P + "002" + Msg.EMPTY_A, R + Msg.EMPTY_A);

        entry.importS('a', 0, 0, 0, 0, 0);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'v'));
        Assertions.assertEquals(0, entry.exportR('a', 'z'));

    }
}
