package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.LoopData;
import org.llschall.ardwloop.structure.data.PostData;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SetupData;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

/**
 * {@link IArdwProgram} should be implemented to create the Java program that communicates
 * with the Arduino program loaded on the Arduino board.
 * <p>
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
public interface IArdwProgram {

    int SC_RC = 9;

    /**
     * setup() is called by the Ardwloop framework on startup, in the same way the setup() function
     * of the C program running in the Arduino board.
     *
     * @param s The {@link SerialData} sent from the Arduino setup() call
     * @return The {@link SerialData} that the Arduino board will receive in its first loop()
     */
    SetupData ardwSetup(SetupData s);

    /**
     * loop() is called by the Ardwloop framework in the same cyclic way as the loop() function
     * of the C program running in the Arduino board.
     *
     * @param s The {@link SerialData} received by the Arduino board
     * @return The {@link SerialData} that will be sent to the Arduino board
     */
    LoopData ardwLoop(LoopData s);

    /**
     * post() is called by the Ardwloop framework each time the Arduino board sent a post {@link SerialData}
     *
     * @param p The received post {@link SerialData}
     */
    default void ardwPost(PostData p) {
        msg("post ignored");
    }

    /**
     * The program id enables to upload a program wrapping several ones on the Arduino board.
     *
     * @return An identifier that can reference the program to be run by the Arduino board,
     */
    default char getProgramId() {
        return 'a';
    }

    /**
     * To be adapted to the Arduino project requirements.
     *
     * @return The size of the {@link SerialData} that the Arduino board will receive.
     */
    @Deprecated
    default int getRc() {
        return SC_RC;
    }


    /**
     * To be adapted to the Arduino project requirements.
     *
     * @return The size of the {@link SerialData} that the Arduino board will send.
     */
    @Deprecated
    default int getSc() {
        return SC_RC;
    }

    /**
     * To be adapted to the Arduino project requirements.
     *
     * @return The polling delay of the Arduino board when it checks if the Java program sent a {@link SerialData}
     */
    default int getReadDelayMs() {
        return 99;
    }

    /**
     * To be adapted to the Arduino project requirements.
     *
     * @return The delay the Arduino board will wait before sending a post {@link SerialData}
     */
    default int getPostDelayMs() {
        return 9999;
    }

}
