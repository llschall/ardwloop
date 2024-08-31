package org.llschall.ardwloop.serial.port

interface ISerialProvider {
    fun listPorts(): List<ISerialPort>
}
