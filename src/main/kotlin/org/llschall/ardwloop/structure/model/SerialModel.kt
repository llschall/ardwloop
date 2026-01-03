package org.llschall.ardwloop.structure.model

import org.llschall.ardwloop.structure.data.ProgramCfg
import org.llschall.ardwloop.structure.data.SerialWrap
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
    val resetPin: AtomicInteger = AtomicInteger(-1)

    @JvmField
    val retryConnection: AtomicBoolean = AtomicBoolean()

    @JvmField
    val program: AtomicReference<ProgramCfg> = AtomicReference()

    @JvmField
    val serialS: AtomicReference<SerialWrap?> = AtomicReference(null)

    @JvmField
    val serialR: AtomicReference<SerialWrap?> = AtomicReference(null)

    @JvmField
    val serialP: AtomicReference<SerialWrap?> = AtomicReference(null)

    @JvmField
    val serialCnt: AtomicInteger = AtomicInteger()
}


