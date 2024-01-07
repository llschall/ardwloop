package structure

class StructureException : RuntimeException {
    constructor(e: Exception?) : super(e)

    constructor(msg: String?) : super(msg)
}
