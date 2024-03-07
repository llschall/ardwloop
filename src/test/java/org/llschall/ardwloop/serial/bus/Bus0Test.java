package org.llschall.ardwloop.serial.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.llschall.ardwloop.JTestProgram;
import org.llschall.ardwloop.LocalOnly;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.Bus;
import org.llschall.ardwloop.serial.Serial;
import org.llschall.ardwloop.serial.misc.FakeProvider;
import org.llschall.ardwloop.serial.misc.IArduino;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;

public class Bus0Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
        LocalOnly.get().skipOnGitHub();
        StructureTimer.FAKE = true;
        BackEntry.setup(new Computer(this));
    }

    @AfterEach
    void close() {
        Logger.msg("*** Closing ***");
        TestTimer.get().delayMs(88);
        BackEntry.close();
        Logger.msg("*** Closed ***");
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void testConnect(int sc) {

        // Arduino <<>> Computer

        ProgramCfg cfg = new ProgramCfg('T', 1, sc, 0, 0);

        ArdwloopModel model = new ArdwloopModel(new ProgramContainer(new JTestProgram()));
        model.serialMdl.program.set(cfg);

        IArduino arduino = new Arduino(this);

        FakeProvider provider = new FakeProvider(arduino);
        Bus bus = new Bus(model, provider);

        Assertions.assertEquals("N/A", model.serialMdl.status.get());

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            bus.connect(cfg);
            Logger.msg("Finished");
        }, "= Computer =");

        NativeEntry entry = new NativeEntry(99, 0, 0, 99, 9);
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup();
            Logger.msg("Finished");
        }, ARDUINO_THD);

        computerThd.start();
        // << Z <<
        TestTimer.get().delayMs(88);
        String c2a = cableC2A.check();
        dump();
        Assertions.assertTrue(c2a.contains(Serial.Z_));
        cableC2A.input.clear();
        dump();

        arduinoThd.start();
        // >> J >>
        TestTimer.get().delayMs(88);
        String a2c = cableA2C.check();
        dump();
        Assertions.assertTrue(a2c.startsWith(Serial.J_));
        cableA2C.releaseAll();

        // << J <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        dump();
        Assertions.assertTrue(c2a.startsWith(Serial.J_));
        cableC2A.release(1);

        // << K <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        dump();
        Assertions.assertEquals(Serial.K_, c2a);
        cableC2A.release(1);

        // >> K >>
        TestTimer.get().delayMs(88);
        a2c = cableA2C.check();
        dump();
        Assertions.assertTrue(a2c.endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CTC1C1C0C0C <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        dump();
        Assertions.assertEquals("CTC1C1C0C0C", c2a);
        cableC2A.release("CTC1C1C0C0C".length());

        TestTimer.get().delayMs(88);

        dump();
        Logger.msg("Finished");
    }

}

