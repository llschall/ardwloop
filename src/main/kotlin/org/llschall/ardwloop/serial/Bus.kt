package serial

import serial.port.GotJException
import serial.port.ISerialProvider
import structure.data.ProgramCfg
import structure.data.SerialData
import structure.model.Model
import structure.utils.Logger.err
import structure.utils.Logger.msg
import structure.utils.Timer

class Bus @JvmOverloads constructor(
    private val model: Model,
    private val provider: ISerialProvider,
    private val timer: Timer = Timer()
) {
    private val serialMdl = model.serialMdl
    private var connected = false
    private var serial: Serial? = null

    fun connect(cfg: ProgramCfg): Boolean {
        serial = Serial(model, cfg, timer)
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
    fun writeR(r: SerialData?) {
        serial!!.writeV(r!!)
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
        opt?.let { serialMdl.serialP.set(it) }
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
