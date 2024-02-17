package org.llschall.ardwloop.jni;

public class NativeEntry {

    static {
        System.loadLibrary("entry");
    }

    public NativeEntry(int log, int reboot, int read, int post, int beforeK, int j) {
        init(log, reboot, read, post, beforeK, j);
    }

    public NativeEntry() {
        this(9, 1, 1, 1, 1, 1);
    }

    public native void init(int log, int reboot, int read, int post, int beforeK, int j);

    public native int ping();

    public native int print();

    public native int check(int i);

    public native void setup();

    public native void loop();

    public native char prg();

    public native int rc();

    public native int sc();

    public native void reset();

    public native int exportR(char v, char d);

    public native void importS(char c, int v, int w, int x, int y, int z);

}