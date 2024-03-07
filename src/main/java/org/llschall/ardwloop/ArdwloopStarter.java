package org.llschall.ardwloop;

import org.llschall.ardwloop.motor.AbstractLoop;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;

public class ArdwloopStarter {

    public static final String ARDWLOOP_VERSION = "0.1.1";
    private static final ArdwloopStarter INSTANCE = new ArdwloopStarter();
    ProgramContainer container;

    private ArdwloopStarter() {
        // Singleton pattern
    }

    public static ArdwloopStarter get() {
        return INSTANCE;
    }

    public ArdwloopModel start(IArdwProgram program, AbstractLoop... loops) {
        Logger.msg("Starting Ardwloop version " + ARDWLOOP_VERSION);
        container = new ProgramContainer(program);
        for (AbstractLoop loop : loops) {
            container.addLoop(loop);
        }
        container.start();
        return container.model;
    }

}
