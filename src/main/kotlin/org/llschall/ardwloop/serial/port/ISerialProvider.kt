package org.llschall.ardwloop.serial.port

import org.llschall.ardwloop.serial.port.ISerialPort

interface ISerialProvider {
    fun listPorts(): List<ISerialPort>
}
