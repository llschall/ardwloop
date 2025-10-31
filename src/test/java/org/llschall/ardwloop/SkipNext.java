package org.llschall.ardwloop;

public class SkipNext {

    private static SkipNext INSTANCE = new SkipNext();

    private SkipNext() {
        // Singleton pattern
    }

    public static SkipNext get() {
        return INSTANCE;
    }

    public boolean skip() {
        return System.getProperty("skip") == null;
    }

}
