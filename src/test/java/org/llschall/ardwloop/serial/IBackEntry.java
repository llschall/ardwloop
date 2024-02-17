package org.llschall.ardwloop.serial;

public interface IBackEntry {
    int available();

    char read();

    void write(char c);
}
