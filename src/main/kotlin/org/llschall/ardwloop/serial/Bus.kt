package org.llschall.ardwloop.serial

import org.llschall.ardwloop.serial.*
import org.llschall.ardwloop.serial.port.*
import org.llschall.ardwloop.structure.*
import org.llschall.ardwloop.structure.data.*
import org.llschall.ardwloop.structure.model.*
import org.llschall.ardwloop.structure.model.keyboard.*
import org.llschall.ardwloop.structure.utils.*
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg

class Bus @JvmOverloads constructor(
    private val model: ArdwloopModel,
    private val provider: ISerialProvider,
    private val timer: Timer = Timer()
) {
    private val serialMdl = model.serialMdl
    private var connected = false
    private var serial: Serial? = null

    fun connect(cfg: ProgramCfg, selector: IArdwPortSelector): Boolean {
        serial = Serial(model, cfg, timer, selector)
        model.serialMdl.status.set("Connecting...")

        try {
            connected = serial!!.connect(provider)
        } catch (e: SerialWriteException) {
            err("Serial connection failed.")
        }
        msg("Serial connection " + (if (connected) "OK" else "failed"))
        return connected
    }

    @Throws(SerialWriteException::class)
    fun writeR(r: SerialData) {
        serial!!.writeV(r)
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun readS(): SerialData {
        val dataS = serial!!.readS()
        serialMdl.serialRS.incrementAndGet()
        return dataS
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun checkP(): SerialData? {
        val opt = serial!!.checkP()
        opt?.let { it: SerialData -> serialMdl.serialP.set(it) }
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
