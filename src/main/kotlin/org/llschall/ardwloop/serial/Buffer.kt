package org.llschall.ardwloop.serial

import org.llschall.ardwloop.serial.*
import org.llschall.ardwloop.serial.port.*
import org.llschall.ardwloop.structure.*
import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.data.*
import org.llschall.ardwloop.structure.model.*
import org.llschall.ardwloop.structure.model.keyboard.*
import org.llschall.ardwloop.structure.utils.*
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Text.ms
import org.llschall.ardwloop.structure.utils.Timer
import java.util.*

internal class Buffer(private val model: Model, private val port: ISerialPort, private val lastRead: Timer) {
    private val chars = LinkedList<Char>()

    @Throws(SerialLongReadException::class)
    fun containsJ(): Boolean {
        try {
            readPort()
            return chars.contains(Serial.J)
        } catch (e: GotJException) {
            return true
        } finally {
            chars.clear()
        }
    }

    @Throws(SerialLongReadException::class)
    fun containsK(): Boolean {
        try {
            readPort(false)
        } catch (e: GotJException) {
            get().fail(e)
        }
        val b = chars.contains(Serial.K)
        if (b) {
            chars.clear()
        }
        return b
    }

    fun ignoreAllNext() {
        chars.clear()
        var n = port.bytesAvailable()
        if (n > 0) {
            val bytes = ByteArray(n)
            port.readBytes(bytes, n.toLong())
            msg("Ignored $n bytes.")
            model.serialMdl.lastReadMs.set(0)
            lastRead.restart()
        }
        get().delayMs(1)
        n = port.bytesAvailable()
        while (n > 0) {
            msg("Remaining: $n")
            val bytes = ByteArray(n)
            port.readBytes(bytes, n.toLong())
            waitRemaining()
            n = port.bytesAvailable()
        }
    }

    private fun waitRemaining() {
        get().delayUs(9)
    }

    fun bytesAvailable(): Boolean {
        return port.bytesAvailable() > 0
    }

    @Throws(SerialLongReadException::class, GotJException::class)
    private fun readPort(failIfGotJ: Boolean = true) {
        val n = port.bytesAvailable()
        if (n == 0 || n == -1) {
            if (lastRead.greaterThanMs(20000)) {
                model.serialMdl.status.set("Read time out")
                model.serialMdl.connected.set(false)
                throw SerialLongReadException("Read time out")
            }
            return
        }
        lastRead.restart()
        val bytes = ByteArray(n)
        port.readBytes(bytes, n.toLong())

        for (aByte in bytes) {
            val c = Char(aByte.toUShort())
            if (c == Serial.J) {
                if (failIfGotJ) {
                    throw GotJException()
                }
                msg("Ignored one J")
            }
            chars.addLast(c)
        }
    }

    @Throws(SerialLongReadException::class, GotJException::class)
    fun read(): Char {
        if (chars.isEmpty()) {
            readPort()
        }

        while (chars.isEmpty()) {
            if (lastRead.greaterThanMs(20000)) {
                val ms = lastRead.ms
                msg("No read since " + ms(ms) + " ...")

                model.monitorMdl.samples.addLast(MonitorSample())
                model.serialMdl.status.set("LONG READ " + ms + "ms")
                get().delayMs(200)
            }

            get().delayMs(1)
            readPort()
        }
        return chars.removeFirst()
    }
}
