package org.llschall.ardwloop.structure.data

class SetupData(s: SerialData) : SerialData(s.chk, s.a, s.b, s.c, s.d, s.e, s.f, s.g, s.h, s.i) {

    constructor(av: Int, aw: Int, ax: Int, ay: Int, az: Int)
            : this(SerialData(0, av, aw, ax, ay, az))

}

class LoopData(sd: SerialData) {
    val data: SerialData = sd
}

class PostData(sd: SerialData) {
    val data: SerialData = sd
}