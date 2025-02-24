package org.llschall.ardwloop.jni;

public class NativeEntry {

    static {
        System.loadLibrary("entry");
    }

    public NativeEntry(boolean injectOnly) {
        inject();
        if (injectOnly) {
            return;
        }
        importS("", 0, 'a', 0, 0, 0, 0, 0);
    }

    public NativeEntry() {
        this(false);
    }

    public native void inject();

    public native int ping(int i);

    public native int pong(int i);

    public native void setup(long baud);

    public native void loop();

    public native char prg();

    public native int resetPin();

    public native int rc();

    public native int sc();

    public native int delayRead();

    public native int delayPost();

    public native void reset();

    public native int exportR(char v, char d);

    public native void importS(String str, int size, char c, int v, int w, int x, int y, int z);

}