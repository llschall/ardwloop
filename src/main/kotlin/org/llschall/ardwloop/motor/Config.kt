package org.llschall.ardwloop.motor

import org.llschall.ardwloop.structure.StructureException
import org.llschall.ardwloop.structure.model.Model
import org.llschall.ardwloop.structure.utils.Logger.msg
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Consumer
import java.util.regex.Pattern

class Config(val model: Model) {
    var baud: Int = -1

    init {
        val configPth = Paths.get("ino/arduino_sw").resolve("cfg.h")
        loadCfgH(configPth)
    }

    private fun loadCfgH(configPth: Path) {
        try {
            val lines = Files.readAllLines(configPth)
            lines.forEach(Consumer { line: String -> this.parse(line) })
            msg("BAUD => $baud")
            model.serialMdl.baud.set(baud)
        } catch (e: IOException) {
            throw StructureException(e)
        }
    }

    private fun parse(line: String) {
        if (line.startsWith("//")) return

        if (line.startsWith("const long BAUD")) {
            val value = parseValue(line)
            baud = value.toInt()
        }
    }

    private fun parseValue(line: String): String {
        val pattern = Pattern.compile("[^\\d]+(\\d+).*")
        val matcher = pattern.matcher(line)
        val b = matcher.find()
        if (!b) {
            throw StructureException("Failed parsing line [$line]")
        }
        return matcher.group(1)
    }
}
