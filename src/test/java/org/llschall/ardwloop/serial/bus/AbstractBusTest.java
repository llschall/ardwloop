package org.llschall.ardwloop.serial.bus;

import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.utils.Logger;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;

public class AbstractBusTest {

    public static final String ARDUINO_THD = "=# Arduino #=";
    public static final String COMPUTER_THD = "=# Computer #=";

    final Cable cableA2C = new Cable("A2C");
    final Cable cableC2A = new Cable("C2A");

    void dump() {
        StringWriter writer = new StringWriter();
        writer.append("\nC2A: ").append(cableC2A.dump());
        writer.append("\nA2C: ").append(cableA2C.dump());
        Logger.msg(writer.toString(), 1);
    }

    void check(int av) {
        if (av > 99) {
            Logger.err("Too many available() calls.");
            StructureTimer.get().shutdown();
        }
        TestTimer.get().delayMs(9);
    }

    void delayMs(int ms) {
        TestTimer.get().delayMs(ms);
    }

    String dumpThd() {

        var names = new HashSet<String>();
        names.add(AbstractBusTest.COMPUTER_THD);
        names.add(AbstractBusTest.ARDUINO_THD);

        StringWriter writer = new StringWriter();

        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
            Thread thread = entry.getKey();
            if (!names.contains(thread.getName())) continue;

            writer.append("\n").append(thread.getName()).append(":\n");
            for (StackTraceElement element : entry.getValue()) {
                String moduleName = element.getModuleName();
                if ("java.base".equals(moduleName)) continue;

                writer.append("\t").append(element.toString()).append("\n");
            }
        }
        return writer.toString();
    }


}
