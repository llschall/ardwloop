package org.llschall.ardwloop;

import org.jetbrains.annotations.Nullable;
import org.llschall.ardwloop.motor.Clock;
import org.llschall.ardwloop.motor.Config;
import org.llschall.ardwloop.structure.IProgram;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.Model;

import java.util.ArrayList;
import java.util.List;

import static org.llschall.ardwloop.structure.utils.Logger.msg;


public abstract class AbstractProgram implements IProgram {

    protected final Model model;
    public final Config config;

    private final List<AbstractLoop> loops = new ArrayList<>();

    public AbstractProgram() {
        Model model = new Model(this);
        model.serialMdl.program.set(new ProgramCfg(getId(), getRc(), getSc()));

        config = new Config(model);
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
}
