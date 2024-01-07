package structure.data

data class ProgramCfg(@JvmField val p: Char, @JvmField val rc: Int, @JvmField val sc: Int) {
    fun p(): Char {
        return p
    }

    fun sc(): Int {
        return sc
    }

    fun rc(): Int {
        return rc
    }
}
