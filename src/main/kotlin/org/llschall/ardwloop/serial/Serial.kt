package org.llschall.ardwloop.serial

import com.fazecast.jSerialComm.SerialPort
import org.llschall.ardwloop.serial.*
import org.llschall.ardwloop.serial.port.*
import org.llschall.ardwloop.structure.*
import org.llschall.ardwloop.structure.data.*
import org.llschall.ardwloop.structure.model.*
import org.llschall.ardwloop.structure.model.keyboard.*
import org.llschall.ardwloop.structure.utils.*
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg

import java.io.StringWriter

/**
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
public class ArdwPortDescriptor(val name: String, val description: String, val systemName: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArdwPortDescriptor

        if (name != other.name) return false
        if (description != other.description) return false
        if (systemName != other.systemName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + systemName.hashCode()
        return result
    }

}

/**
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
interface IArdwPortSelector {
    fun select(desc: ArdwPortDescriptor): Boolean

    fun list(): List<ArdwPortDescriptor>
}

/**
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
public open class DefaultPortSelector : IArdwPortSelector {

    override
    fun select(desc: ArdwPortDescriptor): Boolean {
        if ((desc.name.contains("Arduino")
                    || desc.name.contains("CH340"))
        ) return true

        val name = desc.systemName
        return (name.contains("USB")
                || name.contains("rfcomm")
                || name.contains("ttyACM")
                || name.contains("FAKE")
                )
    }

    override
    fun list(): List<ArdwPortDescriptor> {
        val commPorts = SerialPort.getCommPorts()
        val list = mutableListOf<ArdwPortDescriptor>()
        for (port in commPorts) {
            list.add(
                ArdwPortDescriptor(
                    name = port.descriptivePortName,
                    description = port.portDescription,
                    systemName = port.systemPortName
                )
            )
        }
        return list
    }
}


class Serial internal constructor(
    private val model: ArdwloopModel,
    cfg: ProgramCfg,
    val timer: Timer,
    private val selector: IArdwPortSelector,
    private val monitor: ISerialMonitor,
) {
    private val serialMdl = model.serialMdl
    private var port: ISerialPort? = null
    private var writer: Writer? = null
    private var reader: Reader? = null
    private var connector: Connector? = null

    private val p = cfg.p
    private val read = cfg.read
    private val post = cfg.post

    @Throws(SerialWriteException::class)
    fun reboot() {
        connector!!.reboot(p, read, post)
    }

    @Throws(SerialWriteException::class)
    fun connect(provider: ISerialProvider): Boolean {
        serialMdl.port.name.set("Scanning ...")

        val ports = provider.listPorts()

        if (ports.isEmpty()) {
            serialMdl.status.set("No port found")
            msg("# Serial: No port found !")
            return false
        }

        for (port in ports) {
            val wr = StringWriter()
            msg("# Serial: " + port.descriptivePortName)
            wr.append("# ")
            val arr = arrayOf(
                port.systemPortName,
                port.portDescription,
                port.descriptivePortName,
                "" + port.baudRate,
            )

            val desc = ArdwPortDescriptor(
                name = port.descriptivePortName ?: "",
                systemName = port.systemPortName,
                description = port.portDescription ?: "",
            )
            if (selector.select(desc)) {
                this.port = port
                serialMdl.port.name.set(desc.name)
            }

            for (s in arr) {
                wr.append(s)
                wr.append("&")
            }
            msg(wr.toString())
        }

        if (port == null) {
            serialMdl.port.name.set("No valid port found")
            return false
        }

        msg("Serial port ==> $port")

        serialMdl.port.name.set(port!!.systemPortName)

        val baud = serialMdl.baud.get()
        serialMdl.status.set("Opening...")

        val open = port!!.openPort()

        port!!.baudRate = baud
        msg("BAUD set to " + port!!.baudRate)

        if (!open) {
            err("try sudo chmod 777 /dev/" + port!!.systemPortName)
            serialMdl.status.set("CHMOD")
            return false
        }

        reader = Reader(model, port!!, timer)
        writer = Writer(port!!)

        connector = Connector(model, reader!!, writer!!, monitor)

        msg("PLUG")
        connector!!.reboot(p, read, post)

        return true
    }

    fun close() {
        writer?.writeLastZ()

        if (port != null) {
            port!!.closePort()
            msg(">>> port closed")
        }
        serialMdl.status.set("closed")
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun checkP(): SerialWrap? {
        return reader!!.checkP()
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun readS(): SerialWrap {
        val chk = reader!!.readS()
        val map = reader!!.readMap()
        return SerialWrap(chk, map)
    }

    @Throws(SerialWriteException::class)
    fun writeV(data: SerialWrap) {
        writer!!.writeR()

        for (c in 'a'..'i') {
            val map = data.map.fromChar(c)
            if (map.v != 0) writer!!.write(c, 'v', map.v)
            if (map.w != 0) writer!!.write(c, 'w', map.w)
            if (map.x != 0) writer!!.write(c, 'x', map.x)
            if (map.y != 0) writer!!.write(c, 'y', map.y)
            if (map.z != 0) writer!!.write(c, 'z', map.z)
        }

        writer!!.writeT()
        writer!!.flush()
    }

    companion object {
        const val DELAY_BEFORE_K: Int = 99

        const val J: Char = 'J' // ask communication
        const val K: Char = 'K' // init communication
        const val C: Char = 'C' // configuration
        const val S: Char = 'S' // prefix for S message
        const val R: Char = 'R' // prefix for R message
        const val P: Char = 'P' // prefix for P message
        const val T: Char = 'T' // suffix for data message
        const val N: Char = 'N' // end of chunk
        const val Z: Char = 'Z' // reboot the Arduino board

        const val C_: String = C.toString()
        const val K_: String = K.toString()
        const val J_: String = J.toString()
        const val P_: String = P.toString()
        const val R_: String = R.toString()
        const val T_: String = T.toString()
        const val Z_: String = Z.toString()
    }
}
