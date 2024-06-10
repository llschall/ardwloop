package org.llschall.ardwloop;

import org.llschall.ardwloop.motor.AbstractLoop;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.structure.utils.Timer;

/**
 * The {@link ArdwloopStarter} starts the Java part of the Arduino program.
 */
@SuppressWarnings("unused")
public class ArdwloopStarter {

    /**
     * The version of the Ardwloop library
     */
    public static final String VERSION = "0.1.3";

    /**
     * An integer that might vary with the snapshot
     */
    public static final int VERSION_INT = 1001;

    private static final ArdwloopStarter INSTANCE = new ArdwloopStarter();
    ProgramContainer container;

    private ArdwloopStarter() {
        // Singleton pattern
    }

    /**
     * @return The {@link ArdwloopStarter} instance that can be used to start the Arduino program.
     */
    public static ArdwloopStarter get() {
        return INSTANCE;
    }

    /**
     * @param program The Arduino program to be started
     * @param loops   Some additional loops to be executed as well
     * @return The {@link ArdwloopModel} created by starting the program
     */
    public ArdwloopModel start(IArdwProgram program, AbstractLoop... loops) {
        Logger.msg("Starting Ardwloop version " + VERSION);
        container = new ProgramContainer(program);
        for (AbstractLoop loop : loops) {
            container.addLoop(loop);
        }

        Timer timer = new Timer();
        container.start(timer);
        return container.model;
    }

}
