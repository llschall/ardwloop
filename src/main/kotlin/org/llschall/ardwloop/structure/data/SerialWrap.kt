package org.llschall.ardwloop.structure.data

import org.llschall.ardwloop.value.SerialData
import java.util.concurrent.atomic.AtomicLong

open class SerialWrap(
    @JvmField val chk: Int,
    @JvmField val map: SerialData,
) {
    private val id: Long = NEXT_ID.getAndIncrement()

    companion object {
        private val NEXT_ID = AtomicLong()
    }
}
