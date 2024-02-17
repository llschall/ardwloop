package org.llschall.ardwloop.serial.misc;

import org.llschall.ardwloop.serial.port.ISerialPort;
import org.llschall.ardwloop.serial.port.ISerialProvider;

import java.util.ArrayList;
import java.util.List;

public record FakeProvider(IArduino arduino) implements ISerialProvider {

    @Override
    public List<ISerialPort> listPorts() {
        ArrayList<ISerialPort> list = new ArrayList<>();
        list.add(new FakePort(arduino));
        return list;
    }
}
