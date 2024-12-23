package org.llschall.ardwloop.structure.model

import java.util.concurrent.ConcurrentLinkedQueue

class EventQueue {
    private val events = ConcurrentLinkedQueue<Event>()

    fun next(): Event? {
        return events.poll()
    }
}
