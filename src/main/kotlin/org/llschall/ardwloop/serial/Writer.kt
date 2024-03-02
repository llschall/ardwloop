package org.llschall.ardwloop.serial

import org.llschall.ardwloop.serial.*
import org.llschall.ardwloop.serial.port.*
import org.llschall.ardwloop.structure.*
import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.data.*
import org.llschall.ardwloop.structure.model.*
import org.llschall.ardwloop.structure.model.keyboard.*
import org.llschall.ardwloop.structure.utils.*
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Timer
import java.io.StringWriter
import java.util.*
import java.util.function.Consumer

internal class Writer(private val port: ISerialPort) {
    private val buffer = LinkedList<Char>()

    fun write(c: Char, d: Char, i: Int) {
        write(c)
        write(d)
        write(i, true)
    }

    // connection
    @Throws(SerialWriteException::class)
    fun writeJ() {
        write(Serial.J)
        flush()
    }

    @Throws(SerialWriteException::class)
    fun writeK() {
        write(Serial.K)
        flush()
    }

    // switch program
    @Throws(SerialWriteException::class)
    fun writeC(p: Char, rc: Int, sc: Int, read: Int, post: Int) {
        write(Serial.C)
        write(p)
        write(Serial.C)
        write(map(rc))
        write(Serial.C)
        write(map(sc))
        write(Serial.C)
        write(read, false)
        write(Serial.C)
        write(post, false)
        write(Serial.C)
        flush()
    }

    // reboot
    @Throws(SerialWriteException::class)
    fun writeZ() {
        write(Serial.Z)
        flush()
    }

    fun writeR() {
        write(Serial.R)
    }

    private fun write(v: Int, signed: Boolean) {
        if (v == 0) {
            if (signed) {
                write('+')
            } else {
                write('0')
            }
            return
        }

        val pos = v >= 0

        var t = if (pos) v else -v

        var p = 1

        while (p <= t) p *= 10

        while (p >= 10) {
            p /= 10
            val d = t / p
            write(map(d))
            t -= d * p
        }

        if (signed) {
            write(if (pos) '+' else '-')
        }
    }

    private fun write(c: Char) {
        buffer.addLast(c)
    }

    @Throws(SerialWriteException::class)
    fun flush() {
        val timer = Timer()
        val debug = toString()

        val chunk = 41

        while (!buffer.isEmpty()) {
            var size = buffer.size
            val slice = size > chunk
            if (slice) size = chunk + 1

            val bytes = ByteArray(size)

            for (i in 0 until (if (slice) chunk else size)) {
                val first = buffer.removeFirst()
                bytes[i] = first.code.toByte()
            }

            if (slice) {
                bytes[chunk] = Serial.N.code.toByte()
            }

            val n = port.writeBytes(bytes, size)

            if (slice) {
                waitN()
            }

            if (n != size) {
                err("Writing failed ! $debug")
                throw SerialWriteException()
            }
        }

        val ms = timer.ms.toInt()
        val bytes = debug.length

        msg("Flushed " + bytes + " bytes in " + ms + "ms " + debug)
    }

    private fun waitN() {
        while (true) {
            val bytes = ByteArray(1)
            while (port.bytesAvailable() == 0) {
                get().delayMs(1)
            }

            port.readBytes(bytes, 1)
            val c = Char(bytes[0].toUShort())
            if (c == Serial.N) {
                return
            }
            if (c == Serial.P) {
                msg("got P")
                continue
            }
            msg("Unexpected char $c")
        }
    }

    private fun map(i: Int): Char {
        return when (i) {
            0 -> '0'
            1 -> '1'
            2 -> '2'
            3 -> '3'
            4 -> '4'
            5 -> '5'
            6 -> '6'
            7 -> '7'
            8 -> '8'
            9 -> '9'
            else -> throw StructureException("int $i")
        }
    }

    override fun toString(): String {
        val writer = StringWriter()
        buffer.forEach(Consumer { c: Char? -> writer.append(c!!) })
        return writer.toString()
    }
}
