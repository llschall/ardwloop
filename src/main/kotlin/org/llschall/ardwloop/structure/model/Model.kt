package org.llschall.ardwloop.structure.model

import org.llschall.ardwloop.motor.AbstractProgram
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class Model(program: AbstractProgram?) {
    @JvmField
    val monitorMdl: MonitorModel = MonitorModel()

    @JvmField
    val serialMdl: SerialModel = SerialModel()

    @JvmField
    val keyboardMdl: KeyboardModel = KeyboardModel()

    @JvmField
    val reboot: AtomicBoolean = AtomicBoolean(false)

    @JvmField
    val clock: AtomicInteger = AtomicInteger()

    @JvmField
    val loop: AtomicInteger = AtomicInteger()

    @JvmField
    val config: AtomicReference<String> = AtomicReference("N/A")

    @JvmField
    val program: AtomicReference<AbstractProgram> = AtomicReference()

    @JvmField
    val eventQueue: AtomicReference<EventQueue> = AtomicReference()

    init {
        eventQueue.set(EventQueue())
        program?.let { this.program.set(it) }
    }
}
