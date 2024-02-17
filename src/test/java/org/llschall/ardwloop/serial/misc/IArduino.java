package org.llschall.ardwloop.serial.misc;

public interface IArduino {
    void receive(char c);

    char send();

    int available();
}
