package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.SerialData;

import static org.llschall.ardwloop.structure.utils.Logger.msg;

/**
 * {@link IArdwProgram} should be implemented to create the Java program that communicates
 * with the Arduino program loaded on the Arduino board.
 */
public interface IArdwProgram {

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
     * post() is called by the Ardwloop framework each time the Arduino board sent a post {@link SerialData}
     *
     * @param p The received post {@link SerialData}
     */
    default void post(SerialData p) {
        msg("post ignored");
    }

    /**
     * @return An identifier that can reference the program to be runned by the Arduino board,
     * provided a runner supporting multiple programs is loaded on the Arduino board.
     */
    default char getId() {
        return 'a';
    }

    /**
     * @return The size of the {@link SerialData} that the Arduino board will receive.
     * To be adapted to the Arduino project requirements.
     */
    int getRc();


    /**
     * @return The size of the {@link SerialData} that the Arduino board will send.
     * To be adapted to the Arduino project requirements.
     */
    int getSc();

    /**
     * @return The polling delay of the Arduino board when it checks if the Java program sent a {@link SerialData}
     * To be adapted to the Arduino project requirements.
     */
    int getRead();

    /**
     * @return The delay the Arduino board will wait before sending a post {@link SerialData}
     * To be adapted to the Arduino project requirements.
     */
    int getPost();

}
