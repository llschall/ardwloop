package org.llschall.ardwloop;

import org.llschall.ardwloop.structure.data.LoopData;
import org.llschall.ardwloop.structure.data.SetupData;

public class CheckSetup {

    public static void main(String[] args) {

        ArdwloopStarter.get().start(new IArdwProgram() {
            @Override
            public SetupData ardwSetup(SetupData s) {
                return null;
            }

            @Override
            public LoopData ardwLoop(LoopData s) {
                return null;
            }

            @Override
            public int getRc() {
                return 0;
            }

            @Override
            public int getSc() {
                return 0;
            }

            @Override
            public int getReadDelayMs() {
                return 0;
            }

            @Override
            public int getPostDelayMs() {
                return 0;
            }
        });

    }

}
