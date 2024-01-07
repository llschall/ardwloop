package structure.utils

import java.text.DecimalFormat

object Text {
    @JvmStatic
    fun ms(ms: Long): String {
        val format = DecimalFormat()
        val text = format.format(ms)
        return text + "ms"
    }
}
