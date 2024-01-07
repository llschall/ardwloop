package structure.model

import java.util.concurrent.ConcurrentLinkedQueue

class EventQueue {
    private val events = ConcurrentLinkedQueue<Event>()

    fun add(event: Event) {
        events.add(event)
    }

    fun next(): Event? {
        return events.poll()
    }
}
