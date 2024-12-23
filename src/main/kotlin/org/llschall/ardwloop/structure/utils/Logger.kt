package org.llschall.ardwloop.structure.utils

import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {

    private var skipMsg = false

    @JvmStatic
    fun msg(shift: Int, msg: String) {
        if (skipMsg) {
            return
        }
        log(shift, System.out, msg)
    }

    @JvmStatic
    fun msg(msg: String) {
        if (skipMsg) {
            return
        }
        log(0, System.out, msg)
    }


    @JvmStatic
    fun err(msg: String) {
        log(0, System.err, msg)
    }

    @JvmStatic
    fun err(msg: String, e: Exception) {
        err(msg)
        e.printStackTrace()
    }


    private fun log(shift: Int, out: PrintStream, msg: String) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
        val now = LocalDateTime.now()
        val time = now.format(formatter)

        val thread = Thread.currentThread()
        var name = thread.name
        if (name == "JavaFX Application Thread") {
            name = "JAVA_FX"
        }

        val stack = thread.stackTrace

        for (i in stack.indices) {
            val line = stack[i]
            val string = line.toString()
            if (string.contains("Logger") && !string.contains("Logger.log")) {
                val next = stack[i + 1 + shift]
                val str = next.toString()
                out.println(time + '\t' + name + '\t' + str + '\t' + msg)
            }
        }
    }
}
