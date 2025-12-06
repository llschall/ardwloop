package org.llschall.ardwloop.serial;

import org.junit.jupiter.api.Assertions;
import org.llschall.ardwloop.structure.utils.Logger;

class ResetEntry implements IBackEntry {

    final String rb = "K-CWC12C5C4C03C7C9C";
    int av = 0;

    @Override
    public int available() {
        if (av > rb.length() - 1) {
            System.err.println("ERROR av[" + av + "]");
            Logger.err("FAILURE");
            Assertions.fail();
        }
        char c = rb.charAt(av);
        av++;
        return c == '-' ? 0 : 1;
    }

    @Override
    public char read() {
        av--;
        if (av > rb.length() - 1) {
            System.err.println("ERROR rb[" + av + "]");
            Logger.err("FAILURE");
            Assertions.fail();
        }
        char c = rb.charAt(av);
        System.out.println("JAVA rb[" + av + "] > " + c);
        av++;
        return c;
    }

    @Override
    public void write(char c) {
        System.out.println("JAVA char = " + c);
    }

}
