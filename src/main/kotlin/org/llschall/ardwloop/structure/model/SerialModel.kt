package structure.model

import structure.data.ProgramCfg
import structure.data.SerialData
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class SerialModel {
    @JvmField
    val connected: AtomicBoolean = AtomicBoolean()

    @JvmField
    val port: SerialPort = SerialPort()

    @JvmField
    val serialRS: AtomicInteger = AtomicInteger()

    @JvmField
    val status: AtomicReference<String> = AtomicReference("N/A")

    @JvmField
    val lastReadMs: AtomicLong = AtomicLong()

    @JvmField
    val baud: AtomicInteger = AtomicInteger(-1)

    @JvmField
    val program: AtomicReference<ProgramCfg> = AtomicReference()

    @JvmField
    val serialS: AtomicReference<SerialData?> = AtomicReference(null)

    @JvmField
    val serialR: AtomicReference<SerialData?> = AtomicReference(null)

    @JvmField
    val serialP: AtomicReference<SerialData?> = AtomicReference(null)

    @JvmField
    val serialCnt: AtomicInteger = AtomicInteger()
}


