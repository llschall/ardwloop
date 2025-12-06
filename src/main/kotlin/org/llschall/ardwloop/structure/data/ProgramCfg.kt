package org.llschall.ardwloop.structure.data

data class ProgramCfg(
    @JvmField val p: Char,
    @JvmField val arrc: Int,
    @JvmField val read: Int,
    @JvmField val post: Int,
) {
    fun p(): Char {
        return p
    }

    fun read(): Int {
        return read
    }

    fun post(): Int {
        return post
    }

}
