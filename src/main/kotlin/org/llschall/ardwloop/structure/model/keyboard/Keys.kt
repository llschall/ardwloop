package org.llschall.ardwloop.structure.model.keyboard

import java.util.*

enum class Keys {
    A, Z, E, R, T, Y,
    Q, S, D, F, G, H,
    W, X, C, V, B, N,
    SPACE;

    companion object {
        @JvmStatic
        fun fromChar(c: Char): Keys? {
            if (c == ' ') {
                return SPACE
            }
            val name = c.toString().uppercase(Locale.getDefault())
            for (key in entries) {
                if (key.name == name) {
                    return key
                }
            }
            return null
        }
    }
}
