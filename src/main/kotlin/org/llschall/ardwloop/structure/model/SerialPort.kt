package org.llschall.ardwloop.structure.model

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class SerialPort {
    @JvmField
    val bluetooth: AtomicBoolean = AtomicBoolean()

    @JvmField
    val name: AtomicReference<String> = AtomicReference()
}
