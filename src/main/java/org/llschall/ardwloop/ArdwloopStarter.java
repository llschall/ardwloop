package org.llschall.ardwloop;

import kotlin.jvm.functions.Function2;
import org.llschall.ardwloop.motor.AbstractLoop;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.IArdwPortSelector;
import org.llschall.ardwloop.serial.DefaultPortSelector;
import org.llschall.ardwloop.serial.SerialProvider;
import org.llschall.ardwloop.serial.port.ISerialProvider;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.model.SerialModel;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.structure.utils.Timer;

/**
 * The {@link ArdwloopStarter} starts the Java part of the Arduino program.
 * <p>
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
@SuppressWarnings("unused")
public class ArdwloopStarter {

    /**
     * The version of the Ardwloop library
     */
    public static final String VERSION = "0.3.1";

    /**
     * An integer that might vary with the snapshot
     */
    public static final int VERSION_INT = 1001;

    private static final ArdwloopStarter INSTANCE = new ArdwloopStarter();
    ProgramContainer container;

    private IArdwPortSelector selector = new DefaultPortSelector();

    private int resetPin = -1;

    private ArdwloopStarter() {
        // Singleton pattern
    }

    /**
     * Access the singleton instance
     *
     * @return The {@link ArdwloopStarter} instance that can be used to start the Arduino program.
     */
    public static ArdwloopStarter get() {
        return INSTANCE;
    }

    /**
     * Sets a custom selector for the serial port
     *
     * @param selector The selector that would replace the default one
     */
    public void setSelector(IArdwPortSelector selector) {
        this.selector = selector;
    }

    /**
     * Required to enable board reset onl.
     *
     * @param resetPin The pin connected to the reset (RST) pin,
     *                 for instance with a 650 ohm resistor.
     */
    public void setResetPin(int resetPin) {
        this.resetPin = resetPin;
    }

    /**
     * Entry point of the Ardwloop API
     *
     * @param program The Arduino program to be started
     * @param baud    The baud value that should be the same as the one given on the Arduino side
     * @param loops   Some additional loops to be executed as well
     * @return The {@link ArdwloopModel} created by starting the program
     */
    public ArdwloopModel start(IArdwProgram program, int baud, AbstractLoop... loops) {
        return start(program, baud, ArdwloopStarter.get()::build, loops);
    }

    /**
     * An entry point for customized serial communication
     *
     * @param program The Arduino program to be started
     * @param baud    The baud value that should be the same as the one given on the Arduino side
     * @param builder A function that provides the serial communication material
     * @param loops   Some additional loops to be executed as well
     * @return The {@link ArdwloopModel} created by starting the program
     */
    public ArdwloopModel start(IArdwProgram program, int baud, Function2<SerialModel, Timer, ISerialProvider> builder, AbstractLoop... loops) {

        Logger.msg("Starting Ardwloop version " + VERSION);
        container = new ProgramContainer(program);
        for (AbstractLoop loop : loops) {
            container.addLoop(loop);
        }

        Timer timer = new Timer();
        ISerialProvider provider = builder.invoke(container.model.serialMdl, timer);
        container.start(provider, baud, resetPin, timer, selector);
        return container.model;
    }

    private ISerialProvider build(SerialModel model, Timer timer) {
        return new SerialProvider(model, timer);
    }

}
