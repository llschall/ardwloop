package org.llschall.ardwloop.serial.bus;

import org.llschall.ardwloop.serial.IBackEntry;
import org.llschall.ardwloop.structure.utils.Logger;

// Arduino
class BusEntry implements IBackEntry {

    private final AbstractBusTest busTest;
    int av = 0;

    public BusEntry(AbstractBusTest busTest) {
        this.busTest = busTest;
    }

    @Override
    public int available() {
        int i = busTest.cableC2A.available();
        Logger.msg("Nano.av " + i + " (" + av + ")");
        if (i == 0) {
            av++;
            busTest.check(av);
        } else {
            av = 0;
        }
        return i;
    }

    @Override
    public char read() {
        char c = busTest.cableC2A.pull();
        Logger.msg("Nano.rd " + c);
        return c;
    }

    @Override
    public void write(char c) {
        av++;
        Logger.msg("Nano.wr " + c);
        busTest.cableA2C.push(c);
    }
}
