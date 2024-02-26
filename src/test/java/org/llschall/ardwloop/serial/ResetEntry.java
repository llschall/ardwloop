package org.llschall.ardwloop.serial;

class ResetEntry implements IBackEntry {

    final String rb = "K-CW54";
    int av = 0;

    @Override
    public int available() {
        if (av > rb.length() - 1) {
            System.err.println("ERROR av[" + av + "]");
            System.exit(0);
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
            System.exit(0);
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
