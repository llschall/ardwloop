package org.llschall.ardwloop.serial.misc;

import org.llschall.ardwloop.serial.port.ISerialPort;

public class FakePort implements ISerialPort {

    final IArduino arduino;

    public FakePort(IArduino arduino) {
        this.arduino = arduino;
    }

    @Override
    public int bytesAvailable() {
        return arduino.available();
    }

    @Override
    public void readBytes(byte[] bytes, long n) {
        for (int i = 0; i < n; i++) {
            bytes[i] = (byte) arduino.send();
        }
    }

    @Override
    public int writeBytes(byte[] bytes, int size) {
        for (byte b : bytes) {
            arduino.receive((char) b);
        }
        return size;
    }

    @Override
    public boolean openPort() {
        return true;
    }

    @Override
    public void closePort() {

    }

    @Override
    public String getDescriptivePortName() {
        return "FAKE";
    }

    @Override
    public String getSystemPortName() {
        return "FAKE";
    }

    @Override
    public String getPortDescription() {
        return "FAKE";
    }

    @Override
    public int getBaudRate() {
        return 0;
    }

    @Override
    public void setBaudRate(int baud) {

    }
}
