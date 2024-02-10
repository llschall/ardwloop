package org.llschall.ardwloop.motor;

import org.jetbrains.annotations.Nullable;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.Model;
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractProgram {

    protected Model model;
    public Config config;
    private IArdwProgram program;
    private final List<AbstractLoop> loops = new ArrayList<>();

    protected void init(IArdwProgram program) {
        this.program = program;

        Model model = new Model(this);
        model.serialMdl.program.set(new ProgramCfg(program.getId(), program.getRc(), program.getSc()));

        config = new Config(9600, model);
        this.model = model;
    }

    protected void addLoop(AbstractLoop loop) {
        loops.add(loop);
    }

    protected void start() {
        Clock clock = new Clock(config, loops, model);
        clock.start();
    }

    public final SerialData setupPrg(SerialData s) {
        return program.setup(s);
    }

    public final SerialData loopPrg(KeyboardModel keyboardMdl, SerialData r) {
        return program.loop(keyboardMdl, r);
    }

    public final void postPrg(@Nullable SerialData p) {
        program.post(p);
    }

}
