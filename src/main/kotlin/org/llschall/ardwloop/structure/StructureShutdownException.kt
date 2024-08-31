package org.llschall.ardwloop.structure

object StructureShutdownException : RuntimeException() {
    private fun readResolve(): Any = StructureShutdownException
    fun msg(): String {
        val name = Thread.currentThread().name
        return "### SHUTDOWN requested by $name ###"
    }
}
