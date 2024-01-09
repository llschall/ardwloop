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

internal class Reader(model: Model, port: ISerialPort, timer: Timer) {
    private val serialMdl = model.serialMdl
    private val serialCnt = serialMdl.serialCnt
    val buffer: Buffer = Buffer(model, port, timer)

    fun waitJ() {
        while (true) {
            try {
                if (buffer.containsJ()) {
                    serialMdl.status.set("Received J")
                    return
                }
                get().delayMs(99)
            } catch (e: SerialLongReadException) {
                msg("Ignore $e while waiting for J")
            }
        }
    }

    @Throws(SerialLongReadException::class)
    fun waitK() {
        while (true) {
            if (buffer.containsK()) {
                serialMdl.status.set("Received K")
                return
            }
        }
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun checkP(): SerialData? {
        if (!buffer.bytesAvailable()) {
            return null
        }
        val c = buffer.read()
        if (c != Serial.P) {
            throw SerialWrongReadException("Expected 'P' but got $c")
        }
        return readP()
    }

    @Throws(SerialWrongReadException::class, SerialLongReadException::class, GotJException::class)
    fun readS(): Int {
        var c = buffer.read()

        while (c != Serial.S) {
            if (c == Serial.P) {
                readP()
                err("Ignored P")
            } else {
                throw SerialWrongReadException("Expected 'S' or 'P' but got $c")
            }
            c = buffer.read()
        }

        return readChk()
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    private fun readP(): SerialData {
        val i = readChk()
        val p = read()
        serialCnt.incrementAndGet()
        return SerialData(i, p)
    }

    @Throws(SerialLongReadException::class, GotJException::class, SerialWrongReadException::class)
    private fun readChk(): Int {
        var i = map(buffer.read())
        i *= 10
        i += map(buffer.read())
        i *= 10
        i += map(buffer.read())
        return i
    }

    @Throws(SerialWrongReadException::class, SerialLongReadException::class, GotJException::class)
    fun read(): SerialVector {
        val v = read('a', 'v')
        val w = read('a', 'w')
        val x = read('a', 'x')
        val y = read('a', 'y')
        val z = read('a', 'z')

        return SerialVector(v, w, x, y, z)
    }

    @Throws(SerialWrongReadException::class, SerialLongReadException::class, GotJException::class)
    fun read(id: Char): SerialVector {
        val v = read(id, 'v')
        val w = read(id, 'w')
        val x = read(id, 'x')
        val y = read(id, 'y')
        val z = read(id, 'z')

        return SerialVector(v, w, x, y, z)
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    private fun read(id: Char, data: Char): Int {
        var c = buffer.read()
        if (c != id) {
            throw SerialWrongReadException("Expected '$id' but got $c")
        }
        c = buffer.read()
        if (c != data) {
            throw SerialWrongReadException("Expected '$data' but got $c")
        }

        return readInt()
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    private fun readInt(): Int {
        var v = 0

        while (true) {
            val c = buffer.read()

            when (c) {
                '+' -> {
                    return +v
                }

                '-' -> {
                    return -v
                }

                else -> {
                    val i = map(c)
                    v = 10 * v + i
                }
            }
        }
    }

    @Throws(SerialWrongReadException::class)
    private fun map(c: Char): Int {
        when (c) {
            '0' -> {
                return 0
            }

            '1' -> {
                return 1
            }

            '2' -> {
                return 2
            }

            '3' -> {
                return 3
            }

            '4' -> {
                return 4
            }

            '5' -> {
                return 5
            }

            '6' -> {
                return 6
            }

            '7' -> {
                return 7
            }

            '8' -> {
                return 8
            }

            '9' -> {
                return 9
            }
        }
        throw SerialWrongReadException("char $c")
    }
}
