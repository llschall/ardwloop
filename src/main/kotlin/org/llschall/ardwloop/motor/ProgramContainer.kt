package org.llschall.ardwloop.motor

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.structure.data.ProgramCfg
import org.llschall.ardwloop.structure.data.SerialData
import org.llschall.ardwloop.structure.model.ArdwloopModel

class ProgramContainer(private val program: IArdwProgram) {

    val container: ProgramContainer? = null

    @JvmField
    val model: ArdwloopModel
    private val config: Config
    private val loops: MutableList<AbstractLoop> = ArrayList()

    init {
        val model = ArdwloopModel(this)
        model.serialMdl.program.set(
            ProgramCfg(
                program.id, program.rc, program.sc,
                program.read, program.post,
            )
        )

        config = Config(9600, model)
        this.model = model
    }

    fun addLoop(loop: AbstractLoop) {
        loops.add(loop)
    }

    fun start() {
        val clock = Clock(config, loops, model)
        clock.start()
    }

    fun setupPrg(s: SerialData?): SerialData {
        return program.setup(s)
    }

    fun loopPrg(r: SerialData?): SerialData {
        return program.loop(r)
    }

    fun postPrg(p: SerialData?) {
        program.post(p)
    }
}
