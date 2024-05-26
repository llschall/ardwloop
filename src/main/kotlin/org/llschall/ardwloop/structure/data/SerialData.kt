package org.llschall.ardwloop.structure.data

import java.util.concurrent.atomic.AtomicLong

open class SerialData @JvmOverloads constructor(
    @JvmField val chk: Int,
    a: SerialVector? = SerialVector(),
    b: SerialVector? = SerialVector(),
    c: SerialVector? = SerialVector(),
    d: SerialVector? = SerialVector(),
    e: SerialVector? = SerialVector(),
    f: SerialVector? = SerialVector(),
    g: SerialVector? = SerialVector(),
    h: SerialVector? = SerialVector(),
    i: SerialVector? = SerialVector()
) {
    private val id: Long = NEXT_ID.getAndIncrement()

    @JvmField
    val a: SerialVector? = a

    @JvmField
    val b: SerialVector? = b

    @JvmField
    val c: SerialVector? = c

    @JvmField
    val d: SerialVector? = d

    @JvmField
    val e: SerialVector? = e

    @JvmField
    val f: SerialVector? = f

    @JvmField
    val g: SerialVector? = g

    @JvmField
    val h: SerialVector? = h

    @JvmField
    val i: SerialVector? = i

    constructor(chk: Int, av: Int, aw: Int, ax: Int, ay: Int, az: Int) : this(
        chk, SerialVector(av, aw, ax, ay, az),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector()
    )

    constructor(
        av: Int, aw: Int, ax: Int, ay: Int, az: Int,
        bv: Int, bw: Int, bx: Int, by: Int, bz: Int
    ) : this(
        0,
        SerialVector(av, aw, ax, ay, az),
        SerialVector(bv, bw, bx, by, bz),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector()
    )

    constructor(
        chk: Int,
        av: Int, aw: Int, ax: Int, ay: Int, az: Int,
        bv: Int, bw: Int, bx: Int, by: Int, bz: Int
    ) : this(
        chk,
        SerialVector(av, aw, ax, ay, az),
        SerialVector(bv, bw, bx, by, bz),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector(),
        SerialVector()
    )

    override fun toString(): String {
        return "Serial[" +
                "ref=" + id +
                "a" + a +
                "b" + b +
                "c" + c +
                "d" + d +
                "e" + e +
                "f" + f +
                "g" + g +
                "h" + h +
                "i" + i +
                "]"
    }

    companion object {
        private val NEXT_ID = AtomicLong()
    }
}
