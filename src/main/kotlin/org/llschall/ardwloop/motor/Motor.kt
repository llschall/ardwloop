package org.llschall.ardwloop.motor

import org.llschall.ardwloop.ArdwloopStatus
import org.llschall.ardwloop.serial.Bus
import org.llschall.ardwloop.serial.IArdwPortSelector
import org.llschall.ardwloop.serial.SerialLongReadException
import org.llschall.ardwloop.serial.SerialWriteException
import org.llschall.ardwloop.serial.SerialWrongReadException
import org.llschall.ardwloop.serial.port.GotJException
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.structure.StructureThread
import org.llschall.ardwloop.structure.StructureTimer
import org.llschall.ardwloop.structure.data.SerialWrap
import org.llschall.ardwloop.structure.model.ArdwloopModel
import org.llschall.ardwloop.structure.model.MonitorSample
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Timer
import java.util.concurrent.atomic.AtomicReference

internal class Motor(
    val model: ArdwloopModel,
    provider: ISerialProvider,
    private val selector: IArdwPortSelector
) : AbstractLoop("MOTOR") {
    private var reconnect: Boolean = false

    val bus: Bus = Bus(model, provider, timer)

    init {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                bus.close()
            }
        )
    }

    override fun setup() {
        val serialMdl = model.serialMdl
        val cfg = serialMdl.program.get()

        if (reconnect) {
            bus.close()
        }

        val program = model.program.get()
        serialMdl.connected.set(false)

        program.fireStatusChanged(ArdwloopStatus.CONNECTING)
        bus.reset(cfg, selector)

        while (!bus.connect()) {

            if (!serialMdl.retryConnection.get()) {
                program.fireStatusChanged(ArdwloopStatus.CONNECTION_ABORTED)
                StructureThread.stop.set(true)
                msg("Serial connection failed and retry is switched off.")
                StructureTimer.get().stop()
                return
            }
            program.fireStatusChanged(ArdwloopStatus.CONNECTION_RETRY)

            msg("Waiting 2s before trying to connect again...")

            model.monitorMdl.samples.addLast(MonitorSample())
            StructureTimer.get().delayMs(2000)
            bus.reset(cfg, selector)
        }
        program.fireStatusChanged(ArdwloopStatus.CONNECTED)
        serialMdl.connected.set(true)

        try {
            val s = readS()
            val r = program.setupPrg(s.map)
            writeR(SerialWrap(0, r))
            reconnect = false
        } catch (e: SerialLongReadException) {
            err("Setup error", e)
        } catch (e: SerialWrongReadException) {
            err("Setup error", e)
        } catch (e: SerialWriteException) {
            err("Setup error", e)
        } catch (e: GotJException) {
            StructureTimer.get().fail(e)
        }
    }

    override fun loop() {
        if (reconnect) {
            setup()
        }

        try {
            if (model.reboot.getAndSet(false)) {
                bus.reboot()
            }

            val timer = Timer()

            val serialS = readS()
            val readMs = timer.checkMs()

            val program = model.program.get()

            val atm = AtomicReference<SerialWrap>()

            StructureThread({
                val serialR = program.loopPrg(serialS.map)
                model.loop.incrementAndGet()
                atm.set(SerialWrap(0, serialR))
            }, "program_loop").start()

            while (atm.get() == null) {
                StructureTimer.get().delayMs(1)
                val opt = bus.checkP()
                opt?.let {
                    StructureThread(
                        { program.postPrg(it.map) },
                        "program_post"
                    ).start()
                }
            }

            val loopMs = timer.checkMs()

            val serialR = atm.get()
            writeR(serialR)
            val writeMs = timer.checkMs()

            model.monitorMdl.samples.addLast(MonitorSample(loopMs, readMs, writeMs))
        } catch (e: SerialWriteException) {
            err("Error", e)
            reconnect = true
        } catch (e: SerialLongReadException) {
            err("Error", e)
            reconnect = true
        } catch (e: SerialWrongReadException) {
            err("Error", e)
            reconnect = true
        } catch (e: GotJException) {
            StructureTimer.get().fail(e)
        }
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun readS(): SerialWrap {
        val serialS = bus.readS()
        model.serialMdl.serialS.set(serialS)
        return serialS
    }

    @Throws(SerialWriteException::class)
    fun writeR(r: SerialWrap) {
        bus.writeR(r)
        model.serialMdl.serialR.set(r)
    }

    override fun close() {
        bus.close()
    }
}
