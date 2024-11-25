package org.llschall.ardwloop.serial.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.JTestProgram;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.*;
import org.llschall.ardwloop.serial.misc.FakeProvider;
import org.llschall.ardwloop.serial.misc.IArduino;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.serial.port.GotJException;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.llschall.ardwloop.serial.Serial.*;

public class Bus0Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
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

        ArdwloopModel model = new ArdwloopModel(
                new ProgramContainer(new JTestProgram(), IArdwConfig.BAUD_19200));
        model.serialMdl.program.set(cfg);

        IArduino arduino = new Arduino(this);

        FakeProvider provider = new FakeProvider(arduino);
        Bus bus = new Bus(model, provider);

        Assertions.assertEquals("N/A", model.serialMdl.status.get());

        var finishedA = new AtomicBoolean(false);
        var finishedC = new AtomicBoolean(false);

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            boolean connect = bus.connect(cfg, new DefaultPortSelector());

            Assertions.assertTrue(connect);
            Logger.msg("Loop 1");
            try {
                SerialData s = bus.readS();
                Assertions.assertEquals(0, s.chk);
                bus.writeR(new SerialData(0, 7, 7, 7, 7, 7));
                finishedC.set(true);
            } catch (SerialLongReadException | SerialWrongReadException | GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }
        }, "= Computer =");

        NativeEntry entry = new NativeEntry();
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup(IArdwConfig.BAUD_38400);
            Logger.msg("Finished");
            finishedA.set(true);
        }, ARDUINO_THD);

        computerThd.start();
        // << Z <<
        TestTimer.get().delayMs(88);
        String c2a = cableC2A.check();
        Assertions.assertTrue(c2a.contains(Serial.Z_));
        cableC2A.input.clear();

        arduinoThd.start();
        // >> J >>
        TestTimer.get().delayMs(88);
        String a2c = cableA2C.check();
        Assertions.assertTrue(a2c.startsWith(Serial.J_));
        cableA2C.releaseAll();

        // << J <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Assertions.assertTrue(c2a.startsWith(Serial.J_));
        cableC2A.release(1);

        // << K <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Assertions.assertEquals(Serial.K_, c2a);
        cableC2A.release(1);

        // >> K >>
        TestTimer.get().delayMs(88);
        a2c = cableA2C.check();
        Assertions.assertTrue(a2c.endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CTC1C1C0C0C <<
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Assertions.assertEquals("CTC1C1C0C0C", c2a);
        cableC2A.release("CTC1C1C0C0C".length());

        // >> S >>
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(S + "000av+aw+ax+ay+az+" + T, cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        TestTimer.get().delayMs(99);
        Assertions.assertTrue(finishedC.get());
        Assertions.assertEquals(R + "av7+aw7+ax7+ay7+az7+" + T, cableC2A.check());
        cableC2A.releaseAll();

        TestTimer.get().delayMs(88);

        Assertions.assertTrue(finishedA.get());

        dump();
        Logger.msg("Finished");
    }

}

