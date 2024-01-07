package structure

class StructureThread(task: Runnable?, name: String) : Thread(task, "## $name ##")
