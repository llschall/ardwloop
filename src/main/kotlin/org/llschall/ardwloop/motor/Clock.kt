package org.llschall.ardwloop.motor

import org.llschall.ardwloop.serial.Bus
import org.llschall.ardwloop.serial.IPortSelector
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.structure.StructureShutdownException
import org.llschall.ardwloop.structure.StructureThread
import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.model.ArdwloopModel
import org.llschall.ardwloop.structure.model.Event
import org.llschall.ardwloop.structure.model.EventQueue
import org.llschall.ardwloop.structure.utils.Logger
import org.llschall.ardwloop.structure.utils.Timer

class Clock(
    val provider: ISerialProvider,
    val timer: Timer,
    val config: Config,
    val loops: MutableList<AbstractLoop>,
    val model: ArdwloopModel,
    val selector: IPortSelector,
) {
    val events: EventQueue = model.eventQueue.get()

    fun start() {
        Runtime.getRuntime().addShutdownHook(
            StructureThread({ this.stop() }, "SHUTDOWN")
        )
        StructureThread({
            try {
                this.launch()
            } catch (_: StructureShutdownException) {
                Logger.msg("Shut down internally requested.")
            }
        }, "CLOCK").start()
    }

    private fun stop() {
        get().stop()
        get().sleep(2000)
    }

    private fun launch() {
        val bus = Bus(model, provider, timer)
        val motor = Motor(model, config, bus, selector)

        loops.add(motor)

        for (loop in loops) {
            loop.launch()
        }

        while (!get().interrupted()) {
            for (loop in loops) {
                loop.push()
            }
            model.clock.incrementAndGet()
            get().delayMs(10)

            val next = events.next()
            if (next == Event.REBOOT_CARD) {
                reboot()
            }
        }
    }

    private fun reboot() {
        model.reboot.set(true)
    }
}
