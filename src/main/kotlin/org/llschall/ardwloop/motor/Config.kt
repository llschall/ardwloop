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

class Config(val baud: Int, val model: Model) {
    
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
