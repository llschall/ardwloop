package serial.port

import com.fazecast.jSerialComm.SerialPort
import structure.StructureException
import structure.utils.Timer
import java.util.concurrent.atomic.AtomicLong

data class SerialComPort(val delegate: SerialPort, val timer: Timer, val lastReadMs: AtomicLong) : ISerialPort {
    override fun bytesAvailable(): Int {
        val i = delegate.bytesAvailable()
        if (i == 0) {
            lastReadMs.set(timer.ms)
        } else {
            lastReadMs.set(0)
            timer.restart()
        }
        return i
    }

    override fun readBytes(bytes: ByteArray?, n: Long) {
        if (n == 0L) {
            throw StructureException("No byte available for read")
        }
        delegate.readBytes(bytes, n.toInt())
    }

    override val descriptivePortName: String?
        get() = delegate.descriptivePortName

    override val systemPortName: String
        get() = delegate.systemPortName

    override val portDescription: String?
        get() = delegate.portDescription

    override var baudRate: Int
        get() = delegate.baudRate
        set(baud) {
            delegate.setBaudRate(baud)
        }

    override val cTS: Boolean
        get() = delegate.cts

    override val dCD: Boolean
        get() = delegate.dcd

    override val deviceReadBufferSize: Int
        get() = delegate.deviceReadBufferSize

    override val deviceWriteBufferSize: Int
        get() = delegate.deviceWriteBufferSize

    override fun closePort() {
        delegate.closePort()
    }

    override fun openPort(): Boolean {
        return delegate.openPort()
    }

    override fun writeBytes(bytes: ByteArray?, size: Int): Int {
        return delegate.writeBytes(bytes, size)
    }
}
