package org.llschall.ardwloop.jni;

import org.llschall.ardwloop.JniTestException;

public class NativeEntry {

    static {
        System.loadLibrary("entry");
    }

    public NativeEntry(boolean injectOnly) {
        if (!injectOnly) {
            throw new JniTestException("Should never appear.");
        }
        inject();
    }

    public NativeEntry(int reboot, int read, int post, int beforeK, int j) {
        inject();
        init(reboot, read, post, beforeK, j);
    }

    public NativeEntry() {
        this(1, 1, 1, 1, 1);
    }

    public native void inject();

    public native void init(int reboot, int read, int post, int beforeK, int j);

    public native int ping(int i);

    public native int pong(int i);

    public native void setup();

    public native void loop();

    public native char prg();

    public native int rc();

    public native int sc();

    public native int delayRead();

    public native int delayPost();

    public native void reset();

    public native int exportR(char v, char d);

    public native void importS(char c, int v, int w, int x, int y, int z);

}