package org.llschall.ardwloop.motor

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.serial.IArdwPortSelector
import org.llschall.ardwloop.serial.port.ISerialProvider
import org.llschall.ardwloop.structure.data.ProgramCfg
import org.llschall.ardwloop.structure.model.ArdwloopModel
import org.llschall.ardwloop.structure.utils.Timer
import org.llschall.ardwloop.value.LoopData

class ProgramContainer(private val program: IArdwProgram) {

    @JvmField
    val model: ArdwloopModel
    private val loops: MutableList<AbstractLoop> = ArrayList()

    init {
        val model = ArdwloopModel(this)
        model.serialMdl.program.set(
            ProgramCfg(
                program.programId, program.rc, program.sc,
                program.readDelayMs, program.postDelayMs,
            )
        )

        this.model = model
    }

    fun addLoop(loop: AbstractLoop) {
        loops.add(loop)
    }

    fun start(provider: ISerialProvider, baud: Int, resetPin: Int, timer: Timer, selector: IArdwPortSelector) {
        val clock = Clock(provider, timer, loops, model, selector)
        model.serialMdl.baud.set(baud)
        model.serialMdl.resetPin.set(resetPin)
        clock.start()
    }

    fun setupPrg(s: LoopData): LoopData {
        return program.ardwSetup(s)
    }

    fun loopPrg(r: LoopData?): LoopData {
        return program.ardwLoop(r)
    }

    fun postPrg(p: LoopData?) {
        program.ardwPost(p)
    }
}
