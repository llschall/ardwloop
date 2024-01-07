package structure.data

class SerialVector @JvmOverloads constructor(
    v: Int? = null,
    w: Int? = null,
    x: Int? = null,
    y: Int? = null,
    z: Int? = null
) {
    @JvmField
    val v: Int? = v

    @JvmField
    val w: Int? = w

    @JvmField
    val x: Int? = x

    @JvmField
    val y: Int? = y

    @JvmField
    val z: Int? = z

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
