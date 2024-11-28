package org.llschall.ardwloop.structure.data

class SetupData(s: SerialData) : SerialData(s.chk, s.map, s.a, s.b, s.c, s.d, s.e, s.f, s.g, s.h, s.i) {

    constructor(av: Int)
            : this(SerialData(0, av, 0, 0, 0, 0))

    constructor(av: Int, aw: Int)
            : this(SerialData(0, av, aw, 0, 0, 0))

    constructor(av: Int, aw: Int, ax: Int, ay: Int, az: Int)
            : this(SerialData(0, av, aw, ax, ay, az))

}

class LoopData(s: SerialData) : SerialData(s.chk, s.map, s.a, s.b, s.c, s.d, s.e, s.f, s.g, s.h, s.i) {

    constructor(av: Int)
            : this(SerialData(0, av, 0, 0, 0, 0))

    constructor(av: Int, aw: Int)
            : this(SerialData(0, av, aw, 0, 0, 0))

    constructor(av: Int, aw: Int, ax: Int, ay: Int, az: Int)
            : this(SerialData(0, av, aw, ax, ay, az))

}

class PostData(s: SerialData) : SerialData(s.chk, s.map, s.a, s.b, s.c, s.d, s.e, s.f, s.g, s.h, s.i) {

    constructor(av: Int)
            : this(SerialData(0, av, 0, 0, 0, 0))

    constructor(av: Int, aw: Int)
            : this(SerialData(0, av, aw, 0, 0, 0))

    constructor(av: Int, aw: Int, ax: Int, ay: Int, az: Int)
            : this(SerialData(0, av, aw, ax, ay, az))

}