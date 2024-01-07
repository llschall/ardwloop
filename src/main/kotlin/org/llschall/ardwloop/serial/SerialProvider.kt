package serial

import com.fazecast.jSerialComm.SerialPort
import serial.port.ISerialPort
import serial.port.ISerialProvider
import serial.port.SerialComPort
import structure.model.SerialModel
import structure.utils.Logger.err
import structure.utils.Logger.msg
import structure.utils.Timer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.stream.Collectors

data class SerialProvider(val serialMdl: SerialModel, val timer: Timer) : ISerialProvider {
    override fun listPorts(): List<ISerialPort> {
        try {
            val proc = Runtime.getRuntime().exec(arrayOf("rfcomm", "-a"))
            val `in` = proc.inputStream
            val reader = BufferedReader(InputStreamReader(`in`))
            val list = reader.lines().toList()
            val size = list.size
            `in`.close()
            if (size > 0) {
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
                    }
                    .collect(Collectors.toList())
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
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
}
