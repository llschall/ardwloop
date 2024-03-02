package org.llschall.ardwloop.structure.data

data class ProgramCfg(
    @JvmField val p: Char, @JvmField val rc: Int, @JvmField val sc: Int,
    @JvmField val read: Int, @JvmField val post: Int,
) {
    fun p(): Char {
        return p
    }

    fun sc(): Int {
        return sc
    }

    fun rc(): Int {
        return rc
    }

    fun read(): Int {
        return read
    }

    fun post(): Int {
        return post
    }

}
