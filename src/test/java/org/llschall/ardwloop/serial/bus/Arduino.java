package org.llschall.ardwloop.serial.bus;

import org.llschall.ardwloop.serial.misc.IArduino;
import org.llschall.ardwloop.structure.utils.Logger;

// Commodore side
class Arduino implements IArduino {

    private final AbstractBusTest busTest;
    int av = 0;

    public Arduino(AbstractBusTest busTest) {
        this.busTest = busTest;
    }

    @Override
    public int available() {
        int i = busTest.cableA2C.available();
        Logger.msg("Commodore.av " + i + " (" + av + ")");
        if (i == 0) {
            av++;
            busTest.check(av);
        } else {
            av = 0;
        }
        return i;
    }

    @Override
    public char send() {
        char c = busTest.cableA2C.pull();
        Logger.msg("Commodore.rd " + c);
        return c;
    }

    @Override
    public void receive(char c) {
        Logger.msg("Commodore.wr " + c);
        busTest.cableC2A.push(c);
    }

}
