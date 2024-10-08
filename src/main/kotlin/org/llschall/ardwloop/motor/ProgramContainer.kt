package org.llschall.ardwloop.motor

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.serial.IArdwPortSelector
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.structure.data.LoopData
import org.llschall.ardwloop.structure.data.PostData
import org.llschall.ardwloop.structure.data.ProgramCfg
import org.llschall.ardwloop.structure.data.SetupData
import org.llschall.ardwloop.structure.model.ArdwloopModel
import org.llschall.ardwloop.structure.utils.Timer

class ProgramContainer(private val program: IArdwProgram) {

    @JvmField
    val model: ArdwloopModel
    private val config: Config
    private val loops: MutableList<AbstractLoop> = ArrayList()

    init {
        val model = ArdwloopModel(this)
        model.serialMdl.program.set(
            ProgramCfg(
                program.programId, program.rc, program.sc,
                program.readDelayMs, program.postDelayMs,
            )
        )

        config = Config(9600, model)
        this.model = model
    }

    fun addLoop(loop: AbstractLoop) {
        loops.add(loop)
    }

    fun start(provider: ISerialProvider, timer: Timer, selector: IArdwPortSelector) {
        val clock = Clock(provider, timer, config, loops, model, selector)
        clock.start()
    }

    fun setupPrg(s: SetupData): SetupData {
        return program.ardwSetup(s)
    }

    fun loopPrg(r: LoopData?): LoopData {
        return program.ardwLoop(r)
    }

    fun postPrg(p: PostData?) {
        program.ardwPost(p)
    }
}
