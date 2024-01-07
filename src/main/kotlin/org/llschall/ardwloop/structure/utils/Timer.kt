package structure.utils

class Timer {
    var start: Long = System.nanoTime()

    private var last: Long = start

    fun restart() {
        start = System.nanoTime()
        last = start
    }

    val us: Long
        get() {
            val now = System.nanoTime()
            var duration = now - start
            duration /= 1000
            return duration
        }

    val ms: Long
        get() {
            var duration = us
            duration /= 1000
            return duration
        }

    fun checkMs(): Long {
        val now = System.nanoTime()
        val delta = now - last
        last = now
        return delta / 1000000
    }

    fun smallerThanMs(ms: Long): Boolean {
        return this.ms < ms
    }

    fun greaterThanMs(ms: Long): Boolean {
        return !smallerThanMs(ms)
    }
}
