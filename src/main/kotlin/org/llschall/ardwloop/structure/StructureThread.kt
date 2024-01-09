package org.llschall.ardwloop.structure

class StructureThread(task: Runnable?, name: String) : Thread(task, "## $name ##")
