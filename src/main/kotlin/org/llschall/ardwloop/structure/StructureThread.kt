package org.llschall.ardwloop.structure

import java.util.concurrent.atomic.AtomicBoolean

class StructureThread(task: Runnable?, name: String) : Thread(task, "## $name ##") {

    companion object StaticHolder {
        val stop = AtomicBoolean(false)
    }

}
