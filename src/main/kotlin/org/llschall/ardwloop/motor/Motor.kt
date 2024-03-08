package org.llschall.ardwloop.motor

import org.llschall.ardwloop.serial.*
import org.llschall.ardwloop.serial.port.*
import org.llschall.ardwloop.structure.StructureThread

import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.data.*
import org.llschall.ardwloop.structure.model.*
import org.llschall.ardwloop.structure.model.keyboard.*
import org.llschall.ardwloop.structure.utils.*
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import java.util.concurrent.atomic.AtomicReference

internal class Motor(val model: ArdwloopModel, val config: Config, val bus: Bus) : AbstractLoop("MOTOR") {
    var reconnect: Boolean = false

    override fun setup() {
        val serialMdl = model.serialMdl
        val cfg = serialMdl.program.get()

        if (reconnect) {
            bus.close()
        }

        serialMdl.connected.set(false)
        while (!bus.connect(cfg)) {
            msg("Waiting 2s before trying to connect again...")

            model.monitorMdl.samples.addLast(MonitorSample())
            get().delayMs(2000)
        }
        serialMdl.connected.set(true)

        val program = config.model.program.get()
        try {
            val s = readS()
            val r = program.setupPrg(SetupData(s))
            writeR(r.data)

            reconnect = false
        } catch (e: SerialLongReadException) {
            err("Setup error", e)
        } catch (e: SerialWrongReadException) {
            err("Setup error", e)
        } catch (e: SerialWriteException) {
            err("Setup error", e)
        } catch (e: GotJException) {
            get().fail(e)
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

            val program = config.model.program.get()

            val atm = AtomicReference<SerialData?>()

            StructureThread({
                val serialR = program.loopPrg(LoopData(serialS))
                model.loop.incrementAndGet()
                atm.set(serialR.data)
            }, "program_loop").start()

            while (atm.get() == null) {
                get().delayMs(1)
                val opt = bus.checkP()
                opt?.let {
                    StructureThread(
                        { program.postPrg(PostData(it)) },
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
            get().fail(e)
        }
    }

    @Throws(SerialLongReadException::class, SerialWrongReadException::class, GotJException::class)
    fun readS(): SerialData {
        val serialS = bus.readS()
        model.serialMdl.serialS.set(serialS)
        return serialS
    }

    @Throws(SerialWriteException::class)
    fun writeR(r: SerialData?) {
        bus.writeR(r)
        model.serialMdl.serialR.set(r)
    }

    override fun close() {
        bus.close()
    }
}
