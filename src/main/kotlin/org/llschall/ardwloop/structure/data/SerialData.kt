package org.llschall.ardwloop.structure.data

import org.llschall.ardwloop.value.ArdwData
import java.util.concurrent.atomic.AtomicLong

open class SerialData(
    @JvmField val chk: Int,
    @JvmField val map: ArdwData,
) {
    private val id: Long = NEXT_ID.getAndIncrement()

    companion object {
        private val NEXT_ID = AtomicLong()
    }
}
