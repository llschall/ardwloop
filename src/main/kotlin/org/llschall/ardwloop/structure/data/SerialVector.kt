package org.llschall.ardwloop.structure.data

class SerialVector @JvmOverloads constructor(
    @JvmField val v: Int = 0,
    @JvmField val w: Int = 0,
    @JvmField val x: Int = 0,
    @JvmField val y: Int = 0,
    @JvmField val z: Int = 0,
) {
    
    override fun toString(): String {
        return "" +
                "v" + v +
                "w" + w +
                "x" + x +
                "y" + y +
                "z" + z +
                ""
    }
}
