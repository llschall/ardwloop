package org.llschall.ardwloop.serial

import com.fazecast.jSerialComm.SerialPort
import org.llschall.ardwloop.serial.port.ISerialPort
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.serial.port.SerialComPort
import org.llschall.ardwloop.structure.model.SerialModel
import org.llschall.ardwloop.structure.utils.Logger
import org.llschall.ardwloop.structure.utils.Logger.err
import org.llschall.ardwloop.structure.utils.Logger.msg
import org.llschall.ardwloop.structure.utils.Timer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.stream.Collectors

data class SerialProvider(val serialMdl: SerialModel, val timer: Timer) : ISerialProvider {
    override fun listPorts(): List<ISerialPort> {

        val list = tryRfcomm()
        if (list.isNotEmpty()) {
            return list;
        }

        val ports = SerialPort.getCommPorts()
        return Arrays.stream(ports)
            .map { port: SerialPort? ->
                SerialComPort(
                    port!!, timer, serialMdl.lastReadMs
                )
            }
            .collect(Collectors.toList())
    }

    private fun tryRfcomm(): List<SerialComPort> {

        val os = System.getProperty("os.name")
        if (!os.contains("Linux")) {
            return emptyList()
        }

        msg("Linux detected.")

        try {
            val proc = Runtime.getRuntime().exec(arrayOf("rfcomm", "-a"))
            val `in` = proc.inputStream
            val reader = BufferedReader(InputStreamReader(`in`))
            val list = reader.lines().toList()
            `in`.close()

            if (list.isEmpty()) {
                return emptyList();
            }

            val line = list[0]
            serialMdl.port.bluetooth.set(true)
            if (!line.endsWith("clean ")) {
                serialMdl.port.name.set("Unexpected: $line")
                err(line)
            }
            msg("Bluetooth: $line")
            val ports = arrayOf(
                SerialPort.getCommPort("/dev/rfcomm0")
            )
            return Arrays.stream(ports)
                .map { port: SerialPort? ->
                    SerialComPort(
                        port!!, timer, serialMdl.lastReadMs
                    )
                }.collect(Collectors.toList())

        } catch (e: IOException) {
            Logger.err(e.message.toString())
            return emptyList();
        }
    }

}
