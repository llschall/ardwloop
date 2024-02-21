package org.llschall.ardwloop.motor

import org.llschall.ardwloop.structure.StructureException
import org.llschall.ardwloop.structure.model.ArdwloopModel
import java.util.regex.Pattern

class Config(val baud: Int, val model: ArdwloopModel) {
    
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
