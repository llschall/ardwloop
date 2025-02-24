package org.llschall.ardwloop.serial;

import static org.llschall.ardwloop.serial.Serial.P;
import static org.llschall.ardwloop.serial.Serial.P_;
import static org.llschall.ardwloop.serial.Serial.R;
import static org.llschall.ardwloop.serial.Serial.S;
import static org.llschall.ardwloop.serial.Serial.T;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;

public class Serial4Test {
    @BeforeEach
    void setup() {/* do nothing */ }

    @AfterEach
    void close() {
        BackEntry.close();
    }

    @Test
    void test11() {

        MsgEntry back = new MsgEntry('J', 77, 1, 1);
        BackEntry.setup(back);

        NativeEntry entry = new NativeEntry();
        entry.setup(IArdwConfig.BAUD_300);
        Assertions.assertEquals('J', entry.prg());
        Assertions.assertEquals(1, entry.sc());
        Assertions.assertEquals(1, entry.rc());

        entry.importS("abc", 3, 'a', 0, 0, 54, 0, 0);
        back.addMsg(S + "001~abc~ax54+" + T, R + "av67+aw+ax+ay+az68+" + T);
        entry.loop();
        Assertions.assertEquals(67, entry.exportR('a', 'v'));
        Assertions.assertEquals(68, entry.exportR('a', 'z'));

        entry.importS("", 0, 'a', 0, 0, 0, 0, 0);
        back.addMsg(S + "002" + T, "");
        back.addMsg(P + "000" + T, R + Msg.EMPTY_A + T);
        entry.loop();
        Assertions.assertEquals(0, entry.exportR('a', 'v'));
        Assertions.assertEquals(0, entry.exportR('a', 'z'));

        entry.importS("", 0, 'a', 0, 0, 57, 0, 0);
        back.addMsg(S + "003ax57+" + T, R + "av+aw+ax+ay+az62+" + T);
        entry.loop();
        Assertions.assertEquals(62, entry.exportR('a', 'z'));

        back.addMsg(S + "004" + T, "");
    }
}
