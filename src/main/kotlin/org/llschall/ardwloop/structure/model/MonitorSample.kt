package org.llschall.ardwloop.structure.model

import java.util.concurrent.atomic.AtomicInteger

class MonitorSample {

    @JvmField
    val loopMs: Long

    @JvmField
    val readMs: Long

    @JvmField
    val writeMs: Long

    @JvmField
    val id = nextId.incrementAndGet()

    constructor() {
        loopMs = -1
        readMs = -1
        writeMs = -1
    }

    constructor(loopMs: Long, readMs: Long, writeMs: Long) {
        this.loopMs = loopMs
        this.readMs = readMs
        this.writeMs = writeMs
    }

    companion object {
        val nextId: AtomicInteger = AtomicInteger()
    }
}
