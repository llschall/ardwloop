package org.llschall.ardwloop.motor

import org.llschall.ardwloop.serial.Bus
import org.llschall.ardwloop.serial.SerialProvider
import org.llschall.ardwloop.structure.StructureThread
import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.model.Event
import org.llschall.ardwloop.structure.model.EventQueue
import org.llschall.ardwloop.structure.model.Model
import org.llschall.ardwloop.structure.utils.Timer

class Clock(val config: Config, val loops: MutableList<AbstractLoop>, val model: Model) {
    val events: EventQueue = model.eventQueue.get()

    fun start() {
        Runtime.getRuntime().addShutdownHook(
            StructureThread({ this.stop() }, "SHUTDOWN")
        )
        StructureThread({ this.launch() }, "CLOCK").start()
    }

    fun stop() {
        get().stop()
        get().sleep(2000)
    }

    fun launch() {
        val timer = Timer()
        val bus = Bus(model, SerialProvider(model.serialMdl, timer), timer)
        val motor = Motor(model, config, bus)

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

    fun reboot() {
        model.reboot.set(true)
    }
}
