package org.llschall.ardwloop.serial.bus;

import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.utils.Logger;

import java.io.StringWriter;

public class AbstractBusTest {

    public static final String ARDUINO_THD = "=# Arduino #=";
    public static final String COMPUTER_THD = "=# Computer #=";

    final Cable cableA2C = new Cable("A2C");
    final Cable cableC2A = new Cable("C2A");

    void dump() {
        StringWriter writer = new StringWriter();
        writer.append("\nC2A: ").append(cableC2A.dump());
        writer.append("\nA2C: ").append(cableA2C.dump());
        Logger.msg(1, writer.toString());
    }

    void check(int av) {
        if (av > 99) {
            Logger.err("Too many available() calls.");
            StructureTimer.get().shutdown();
        }
        TestTimer.get().delayMs(9);
    }
}
