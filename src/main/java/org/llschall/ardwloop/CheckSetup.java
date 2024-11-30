package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.LoopData;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SetupData;
import org.llschall.ardwloop.structure.utils.Logger;

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

        ArdwloopStarter.get().start(new IArdwProgram() {
            @Override
            public SetupData ardwSetup(SetupData s) {

                Logger.msg("""
                        \n
                        * * * * * * * * * * * * * * * * * * * * *
                         * *   Setup successfully triggered  * *
                        * * * * * * * * * * * * * * * * * * * * *
                        """);

                StructureTimer.get().shutdown();

                // dead code
                return new SetupData(new SerialData(0, 0, 0, 0, 0, 0));
            }

            @Override
            public LoopData ardwLoop(LoopData s) {
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
        }, IArdwConfig.BAUD_19200);

    }

}
