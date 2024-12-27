package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.value.SerialData;

/**
 * Intended to check the connection on start-up only.
 */
class CheckSetup {

    private CheckSetup() {
        // never called
    }

    /**
     * Checks of the Serial connection is functional.
     *
     * @param args: ignored
     */
    public static void main(String[] args) {

        Logger.msg("Java: " + System.getProperty("java.runtime.version"));
        new CheckSetup().start();
    }

    void start() {

        ArdwloopStarter.get().start(new IArdwProgram() {
            @Override
            public SerialData ardwSetup(SerialData s) {

                Logger.msg("""
                        \n
                        * * * * * * * * * * * * * * * * * * * * *
                         * *   Setup successfully triggered  * *
                        * * * * * * * * * * * * * * * * * * * * *
                        """);

                StructureTimer.get().shutdown();

                // dead code
                return new SerialData();
            }

            @Override
            public SerialData ardwLoop(SerialData s) {
                return null;
            }

            @Override
            public int getReadDelayMs() {
                return 0;
            }

            @Override
            public int getPostDelayMs() {
                return 0;
            }
        }, IArdwConfig.BAUD_9600);
    }

}
