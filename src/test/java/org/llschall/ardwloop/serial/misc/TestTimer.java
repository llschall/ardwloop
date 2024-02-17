package org.llschall.ardwloop.serial.misc;

public class TestTimer {

    public static final TestTimer INSTANCE = new TestTimer();

    private TestTimer() {
        // singleton
    }

    public static TestTimer get() {
        return INSTANCE;
    }

    public void delayMs(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
