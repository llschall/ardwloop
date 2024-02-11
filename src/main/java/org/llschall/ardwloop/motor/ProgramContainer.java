package org.llschall.ardwloop.motor;

import org.jetbrains.annotations.Nullable;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.Model;

import java.util.ArrayList;
import java.util.List;


public class ProgramContainer {

    public final Model model;
    private final Config config;
    private final IArdwProgram program;
    private final List<AbstractLoop> loops = new ArrayList<>();

    public ProgramContainer(IArdwProgram program) {
        this.program = program;

        Model model = new Model(this);
        model.serialMdl.program.set(new ProgramCfg(program.getId(), program.getRc(), program.getSc()));

        config = new Config(9600, model);
        this.model = model;
    }

    public void addLoop(AbstractLoop loop) {
        loops.add(loop);
    }

    public void start() {
        Clock clock = new Clock(config, loops, model);
        clock.start();
    }

    public final SerialData setupPrg(SerialData s) {
        return program.setup(s);
    }

    public final SerialData loopPrg(SerialData r) {
        return program.loop(r);
    }

    public final void postPrg(@Nullable SerialData p) {
        program.post(p);
    }

}
