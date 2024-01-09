package org.llschall.ardwloop.structure.model

import java.util.concurrent.ConcurrentLinkedDeque

class MonitorModel {
    @JvmField
    val samples: ConcurrentLinkedDeque<MonitorSample> = ConcurrentLinkedDeque()
}
