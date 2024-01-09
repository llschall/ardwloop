package org.llschall.ardwloop.motor

import org.llschall.ardwloop.AbstractLoop
import serial.Bus
import serial.SerialProvider
import structure.StructureThread
import structure.StructureTimer.Companion.get
import structure.model.Event
import structure.model.EventQueue
import structure.model.Model
import structure.utils.Timer

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
