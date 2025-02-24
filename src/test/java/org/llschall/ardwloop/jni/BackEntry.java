package org.llschall.ardwloop.jni;

import org.llschall.ardwloop.serial.IBackEntry;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.structure.StructureException;
import org.llschall.ardwloop.structure.StructureShutdownException;
import org.llschall.ardwloop.structure.StructureTimer;
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
        Logger.msg(1, str);
    }

    // called by NativeEntry.c
    static void delay(long ms) {
        try {
            TestTimer.get().delayMs(ms);
        } catch (StructureShutdownException e) {
            throw e;
        } catch (Exception e) {
            Logger.err("Error in delay()", e);
            throw new StructureException(e.getMessage());
        }
    }

    // called by NativeEntry.c
    static int available() {
        try {
            return delegate.available();
        } catch (StructureShutdownException e) {
            throw e;
        } catch (Exception e) {
            Logger.err("Error in available()", e);
            StructureTimer.get().fail(e);
            return -1;
        }
    }

    // called by NativeEntry.c
    static char read() {
        try {
            char read = delegate.read();
            System.out.print(read);
            return read;
        } catch (Exception e) {
            Logger.err("Error in read()", e);
            System.exit(0);
            throw new StructureException("Should never appear.");
        }
    }

    // called by NativeEntry.c
    static void write(char c) {
        try {
            delegate.write(c);
        } catch (Exception e) {
            Logger.err("Error in write() " + c, e);
            System.exit(0);
        }
    }
}
