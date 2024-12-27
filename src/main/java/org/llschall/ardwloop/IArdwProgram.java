package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialWrap;
import org.llschall.ardwloop.value.SerialData;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

/**
 * {@link IArdwProgram} should be implemented to create the Java program that communicates
 * with the Arduino program loaded on the Arduino board.
 * <p>
 * See <a href="https://llschall.github.io/ardwloop">ardwloop pages</a>
 */
public interface IArdwProgram {

    /**
     * The size of the received and sent messages.
     */
    int SC_RC = 9;

    /**
     * setup() is called by the Ardwloop framework on startup, in the same way the setup() function
     * of the C program running in the Arduino board.
     *
     * @param s The {@link SerialData} sent from the Arduino setup() call
     * @return The {@link SerialData} that the Arduino board will receive in its first loop()
     */
    SerialData ardwSetup(SerialData s);

    /**
     * loop() is called by the Ardwloop framework in the same cyclic way as the loop() function
     * of the C program running in the Arduino board.
     *
     * @param s The {@link SerialData} received by the Arduino board
     * @return The {@link SerialData} that will be sent to the Arduino board
     */
    SerialData ardwLoop(SerialData s);

    /**
     * post() is called by the Ardwloop framework each time the Arduino board sent a post {@link SerialWrap}
     *
     * @param p The received post {@link SerialWrap}
     */
    default void ardwPost(SerialData p) {
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
     * @return The polling delay of the Arduino board when it checks if the Java program sent a {@link SerialWrap}
     */
    default int getReadDelayMs() {
        return 99;
    }

    /**
     * To be adapted to the Arduino project requirements.
     *
     * @return The delay the Arduino board will wait before sending a post {@link SerialWrap}
     */
    default int getPostDelayMs() {
        return 9999;
    }

}
