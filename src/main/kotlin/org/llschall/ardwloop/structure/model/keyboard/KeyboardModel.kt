package structure.model.keyboard

import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean

class KeyboardModel {
    @JvmField
    val focused: AtomicBoolean = AtomicBoolean(false)

    @JvmField
    val events: ArrayBlockingQueue<Event> = ArrayBlockingQueue(10000)

    @JvmField
    val map: MutableMap<Keys, AtomicBoolean> = EnumMap(structure.model.keyboard.Keys::class.java)

    init {
        for (key in Keys.entries) {
            map[key] = AtomicBoolean(false)
        }
    }

    fun isPressed(key: Keys): Boolean {
        return map[key]!!.get()
    }
}
