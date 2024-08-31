package org.llschall.ardwloop.hex

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * An ArdwHexUploader uploads a program to an Arduino board using avrdude.
 * See https://github.com/avrdudes/avrdude
 */
class ArdwHexUploader() {

    constructor(
        avrdudePath: String = "avrdude",
        device: String = "atmega328p",
    ) : this() {
        this.avrdudePath = avrdudePath
        this.device = device
    }

    private lateinit var avrdudePath: String
    private lateinit var device: String

    @Throws(IOException::class)
    fun upload(path: String, port: String) {
        val builder = ProcessBuilder(
            avrdudePath,
            "-p", device,
            "-c", "arduino",
            "-P", port,
            "-b", "57600",
            "-U", "flash:w:$path:i"
        )
        val process = builder.start()
        BufferedReader(InputStreamReader(process.errorStream)).lines()
            .forEach(({ x: String -> System.err.println(x) }))
        BufferedReader(InputStreamReader(process.inputStream)).lines()
            .forEach(({ x: String -> println(x) }))
    }
}
