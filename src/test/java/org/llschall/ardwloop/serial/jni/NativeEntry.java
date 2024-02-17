package org.llschall.ardwloop.serial.jni;

public class NativeEntry {

    static {
        System.loadLibrary("entry");
    }

    public native int ping();

}