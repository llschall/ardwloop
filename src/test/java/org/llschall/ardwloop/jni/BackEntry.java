package org.llschall.ardwloop.jni;

import org.junit.jupiter.api.Assertions;
import org.llschall.ardwloop.serial.IBackEntry;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.structure.utils.Logger;

public class BackEntry {

    private static IBackEntry delegate;

    public static void setup(IBackEntry entry) {
        delegate = entry;
        Logger.msg("Delegate is set to " + entry);
    }

    public static void close() {
        Logger.msg("Delegate is closed");
        delegate = null;
    }

    // Called from JNI tests
    public static int pong(int i) {
        Logger.msg("\n****************\n*     PONG     *\n*     " + i + "     *\n****************\n");
        return i;
    }

    public static void msg(String str) {
        Logger.msg(str, 1);
    }

    // called by NativeEntry.c
    static void delay(long ms) {
        try {
            TestTimer.get().delayMs(ms);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    // called by NativeEntry.c
    static int available() {
        try {
            return delegate.available();
        } catch (Exception e) {
            Assertions.fail();
            return 0;
        }
    }

    // called by NativeEntry.c
    static char read() {
        try {
            return delegate.read();
        } catch (Exception e) {
            Assertions.fail();
            return 0;
        }
    }

    // called by NativeEntry.c
    static void write(char c) {
        try {
            delegate.write(c);
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
