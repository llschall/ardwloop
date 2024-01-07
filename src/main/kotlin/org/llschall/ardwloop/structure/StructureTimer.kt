package structure

import structure.utils.Logger
import java.io.StringWriter
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.system.exitProcess

class StructureTimer private constructor() {
    private val run = AtomicBoolean(true)

    fun stop() {
        run.set(false)
        Logger.msg("stop run")
    }

    fun shutdown() {
        val name = Thread.currentThread().name

        val writer = StringWriter()
        writer.append("\n")
        writer.append("### >>>>>>>>>>>>>>>>>>>>\n")
        writer.append("### >>>>> SHUTDOWN requested by ").append(name).append("\n")
        writer.append("### >>>>>>>>>>>>>>>>>>>>")

        Logger.msg(writer.toString())
        exitProcess(0)
    }

    fun interrupted(): Boolean {
        if (run.get()) {
            return false
        }
        Logger.msg("interrupted")
        return true
    }

    fun delayMs(ms: Int) {
        if (FAKE) {
            sleep1Ms()
            sleep1Ms()
            sleep1Ms()
            sample()
            return
        }
        for (i in 0 until ms) {
            sleep1Ms()
        }
    }

    private fun sleep1Ms() {
        try {
            if (interrupted()) {
                throw StructureShutdownException
            }
            Thread.sleep(1)
            if (interrupted()) {
                throw StructureShutdownException
            }
        } catch (e: InterruptedException) {
            fail(Exception("Interrupted waiting"))
        }
    }

    fun delayUs(us: Int) {
        if (FAKE) {
            Logger.msg("Ignore delayMs us")
            return
        }
        try {
            for (i in 0 until us) {
                Thread.sleep(0, 1000)
                if (interrupted()) {
                    throw StructureShutdownException
                }
            }
        } catch (e: InterruptedException) {
            fail(e)
        }
    }

    fun sleep(ms: Long) {
        try {
            Thread.sleep(ms)
        } catch (e: InterruptedException) {
            Logger.err(e.message!!)
        }
    }

    fun fail(e: Exception) {
        Logger.err("ERROR: " + e.message, e)
        shutdown()
    }

    companion object {
        private val INSTANCE: StructureTimer = StructureTimer()

        @JvmField
        var FAKE: Boolean = false
        private const val MONITOR: Boolean = false

        @JvmStatic
        fun get(): StructureTimer {
            return INSTANCE
        }


        fun sample() {
            if (!MONITOR) {
                return
            }
            val name = "# Arduino #"
            if (Thread.currentThread().name == name) {
                return
            }
            val traces = Thread.getAllStackTraces()
            val first = traces.keys.stream().filter { th: Thread -> th.name == name }.findFirst()
            if (first.isEmpty) {
                Logger.msg("Thread not found !")
                return
            }
            val stack = traces[first.get()]!!
            val writer = StringWriter()
            for (i in 0..8) {
                writer.append(stack[i].toString())
                writer.append("\n")
            }
            Logger.msg(writer.toString())
        }
    }
}
