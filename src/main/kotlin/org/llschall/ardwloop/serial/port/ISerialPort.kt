package org.llschall.ardwloop.serial.port

interface ISerialPort {
    fun bytesAvailable(): Int

    fun readBytes(bytes: ByteArray?, n: Long)

    val descriptivePortName: String?

    val systemPortName: String

    val portDescription: String?

    var baudRate: Int

    val cTS: Boolean

    val dCD: Boolean

    val deviceReadBufferSize: Int

    val deviceWriteBufferSize: Int

    fun closePort()

    fun openPort(): Boolean

    fun writeBytes(bytes: ByteArray?, size: Int): Int
}
