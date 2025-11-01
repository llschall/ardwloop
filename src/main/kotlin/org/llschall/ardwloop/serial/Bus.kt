package org.llschall.ardwloop.serial

import org.llschall.ardwloop.serial.port.GotJException
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.structure.data.ProgramCfg
import org.llschall.ardwloop.structure.data.SerialWrap
import org.llschall.ardwloop.structure.model.ArdwloopModel
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Timer

class Bus @JvmOverloads constructor(
    private val model: ArdwloopModel,
    private val provider: ISerialProvider,
    private val timer: Timer = Timer(),
    private val monitor: ISerialMonitor = DefaultSerialMonitor(),
) {
    private val serialMdl = model.serialMdl
    private var connected = false
    private var serial: Serial? = null

    fun reset(cfg: ProgramCfg, selector: IArdwPortSelector) {
        serial = Serial(model, cfg, timer, selector, monitor)
    }

    fun connect(): Boolean {
        model.serialMdl.status.set("Connecting...")

        try {
            connected = serial!!.connect(provider)
        } catch (e: SerialWriteException) {
            err("Serial connection failed: " + e.message)
        }
        msg("Serial connection " + (if (connected) "OK" else "failed"))
        return connected
    }

    @Throws(SerialWriteException::class)
    fun writeR(r: SerialWrap) {
        serial!!.writeV(r)
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun readS(): SerialWrap {
        val dataS = serial!!.readS()
        serialMdl.serialRS.incrementAndGet()
        return dataS
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun checkP(): SerialWrap? {
        val opt = serial!!.checkP()
        opt?.let { it: SerialWrap -> serialMdl.serialP.set(it) }
        return opt
    }

    fun close() {
        msg(">>>>>>>> Serial closing... >>>>>>>>")
        serial!!.close()
        msg(">>>>>>>> Serial closed. >>>>>>>>")
    }

    @Throws(SerialWriteException::class)
    fun reboot() {
        serial!!.reboot()
        msg(">>>>>>>> Reboot >>>>>>>>")
    }
}

interface ISerialMonitor {
    fun fireZSent()
}

private class DefaultSerialMonitor : ISerialMonitor {
    override fun fireZSent() {
        // Do nothing.
    }
}