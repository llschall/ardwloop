package org.llschall.ardwloop.motor


import org.llschall.ardwloop.structure.StructureShutdownException
import org.llschall.ardwloop.structure.StructureThread
import org.llschall.ardwloop.structure.StructureTimer.Companion.get
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Timer
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

abstract class AbstractLoop protected constructor(private val name: String) {
    private val queue: BlockingQueue<Any> = ArrayBlockingQueue(1)
    private val time: AtomicLong = AtomicLong(-1)
    private val thread: Thread
    val timer: Timer = Timer()

    init {
        thread = StructureThread({
            msg("====== Setup... ======")
            var loop = true

            try {
                setup()
            } catch (e: StructureShutdownException) {
                loop = false
            }

            if (StructureThread.stop.get()) {
                loop = false
            }

            msg("====== Setup OK ======")

            while (loop) {
                try {
                    timer.restart()
                    loop()
                    val us = timer.us
                    time.set(us)
                    loop = poll()
                } catch (e: StructureShutdownException) {
                    msg("*** shut down ***")
                    loop = false
                }
            }
            msg("=== Closing... ===")
            close()
            msg("=== Closing OK ===")
        }, name)
    }

    fun launch() {
        msg("Launching $name ...")
        thread.start()
        msg("Launching $name OK")
    }

    private fun poll(): Boolean {
        var poll = queue.poll()
        while (poll == null) {
            if (get().interrupted()) {
                msg("*** Structure interruption ***")
                return false
            }
            try {
                poll = queue.poll(1, TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                err("*** Java interruption ***", e)
                return false
            }
        }
        return true
    }

    fun push() {
        queue.offer(Any())
        val alive = thread.isAlive
        if (!alive) {
            err("Thread is not alive: " + thread.name)
            get().shutdown()
        }
    }

    open fun setup() {
        // do nothing
    }

    abstract fun loop()

    abstract fun close()
}
