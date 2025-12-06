package org.llschall.ardwloop.structure.utils

import java.io.PrintStream
import java.util.Calendar
import java.util.TimeZone

object Logger {

    @JvmStatic
    var level: Int = 0

    @JvmStatic
    fun msg(msg: String, shift: Int) {
        if (level < 1) {
            return
        }
        log(shift, System.out, msg)
    }

    @JvmStatic
    fun msg(msg: String) {
        if (level < 1) {
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
        val time = LoggerDate().asString()

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

class LoggerDate {

    fun asString(): String {
        val instance = Calendar.getInstance(TimeZone.getDefault())
        val hh = addZero(2, instance.get(Calendar.HOUR_OF_DAY))
        val mm = addZero(2, instance.get(Calendar.MINUTE))
        val ss = addZero(2, instance.get(Calendar.SECOND))
        val ms = addZero(3, instance.get(Calendar.MILLISECOND))
        return "$hh:$mm:$ss.$ms"
    }

    fun addZero(size: Int, value: Int): String {

        if (size == 2 && value < 10) {
            return "0$value"
        }
        if (size == 3 && value < 100) {
            return if ((value < 10)) {
                "00$value"
            } else {
                "0$value"
            }
        }
        return "$value"
    }

}