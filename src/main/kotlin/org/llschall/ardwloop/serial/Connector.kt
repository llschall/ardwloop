package serial

import structure.StructureTimer.Companion.get
import structure.model.Model
import structure.model.MonitorSample
import structure.utils.Logger.err
import structure.utils.Logger.msg
import structure.utils.Timer

internal data class Connector(val model: Model, val reader: Reader, val writer: Writer) {
    @Throws(SerialWriteException::class)
    fun reboot(p: Char, rc: Int, sc: Int) {
        val timer = Timer()

        val serialMdl = model.serialMdl

        writer.writeZ()
        status("REBOOT (sent Z)")

        serialMdl.connected.set(false)
        while (timer.smallerThanMs(20000)) {
            model.monitorMdl.samples.addLast(MonitorSample())

            initJ()
            try {
                // wait all the Js are received
                get().delayMs(Serial.DELAY_BEFORE_K)
                reader.buffer.ignoreAllNext()
                // wait to avoid the K to be lost
                get().delayMs(Serial.DELAY_BEFORE_K)
                initK(p, rc, sc)
                serialMdl.connected.set(true)
                return
            } catch (e: SerialLongReadException) {
                err("Long read during reset()")
            }
        }
        throw SerialWriteException()
    }

    @Throws(SerialWriteException::class)
    private fun initJ() {
        reader.buffer.ignoreAllNext()
        status("REBOOT (wait J)")
        reader.waitJ()
        writer.writeJ()
    }

    @Throws(SerialLongReadException::class, SerialWriteException::class)
    private fun initK(p: Char, rc: Int, sc: Int) {
        val serialMdl = model.serialMdl

        serialMdl.connected.set(false)
        msg("== SERIAL CONNECTION INIT ==")
        reader.buffer.ignoreAllNext()
        writer.writeK()

        serialMdl.status.set("Sent K")
        msg("write K")
        get().delayMs(99)

        msg("K ?")
        val timer = Timer()
        serialMdl.status.set("Wait K")
        reader.waitK()
        msg("K ! " + timer.ms + "ms")
        serialMdl.status.set("Received K")

        msg("== SERIAL CONNECTION OK ==")

        writer.writeC(p, rc, sc)
    }

    private fun status(status: String) {
        val serialMdl = model.serialMdl
        serialMdl.status.set(status)
        msg(status)
    }
}
