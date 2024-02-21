package org.llschall.ardwloop;

import org.llschall.ardwloop.motor.AbstractLoop;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.structure.model.ArdwloopModel;

public class ArdwloopStarter {

    ProgramContainer container;
    private static final ArdwloopStarter INSTANCE = new ArdwloopStarter();

    private ArdwloopStarter() {
        // Singleton pattern
    }

    public static ArdwloopStarter get() {
        return INSTANCE;
    }

    public ArdwloopModel start(IArdwProgram program, AbstractLoop... loops) {
        container = new ProgramContainer(program);
        for (AbstractLoop loop : loops) {
            container.addLoop(loop);
        }
        container.start();
        return container.model;
    }

}
