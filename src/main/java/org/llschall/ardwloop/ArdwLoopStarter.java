package org.llschall.ardwloop;

import org.llschall.ardwloop.motor.AbstractLoop;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.structure.model.Model;

public class ArdwLoopStarter {

    ProgramContainer container;
    private static final ArdwLoopStarter INSTANCE = new ArdwLoopStarter();

    private ArdwLoopStarter() {
        // Singleton pattern
    }

    public static ArdwLoopStarter get() {
        return INSTANCE;
    }

    public Model start(IArdwProgram program, AbstractLoop refresher) {
        container = new ProgramContainer(program);
        container.addLoop(refresher);
        container.start();
        return container.model;
    }

}
