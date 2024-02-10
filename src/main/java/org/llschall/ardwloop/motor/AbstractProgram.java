package org.llschall.ardwloop.motor;

import org.jetbrains.annotations.Nullable;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.IProgram;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.Model;
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel;
import sandbox.org.llschall.ardwloop.motor.AbstractLoop;

import java.util.ArrayList;
import java.util.List;

import static org.llschall.ardwloop.structure.utils.Logger.msg;


public abstract class AbstractProgram implements IProgram {

    protected final Model model;
    public final Config config;
    private IArdwProgram program;
    private final List<AbstractLoop> loops = new ArrayList<>();

    protected void init(IArdwProgram program) {
        this.program = program;
    }

    public AbstractProgram(int baud) {

        Model model = new Model(this);
        model.serialMdl.program.set(new ProgramCfg(getId(), getRc(), getSc()));

        config = new Config(baud, model);
        this.model = model;
    }

    protected void addLoop(AbstractLoop loop) {
        loops.add(loop);
    }

    protected void start() {
        Clock clock = new Clock(config, loops, model);
        clock.start();
    }

    @Override
    public void post(@Nullable SerialData p) {
        msg("post ignored");
    }

    public final SerialData loopPrg(KeyboardModel keyboardMdl, SerialData r) {
        return program.loop(keyboardMdl, r);
    }

}
