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

import static org.llschall.ardwloop.serial.Serial.T;

public class Bus1Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
        StructureTimer.FAKE = true;
        BackEntry.setup(new Computer(this));
    }

    @AfterEach
    void close() {
        Logger.msg("*** Closing ***");
        TestTimer.get().delayMs(99);
        BackEntry.close();
        Logger.msg("*** Closed ***");
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void testConnect(int sc) {

        // Arduino <<>> Computer

        ProgramCfg cfg = new ProgramCfg('T', 1, sc, 1, 999);

        ArdwloopModel model = new ArdwloopModel(
                new ProgramContainer(new JTestProgram(), IArdwConfig.BAUD_38400));
        model.serialMdl.program.set(cfg);

        IArduino arduino = new Arduino(this);

        FakeProvider provider = new FakeProvider(arduino);
        Bus bus = new Bus(model, provider);

        Assertions.assertEquals("N/A", model.serialMdl.status.get());

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            boolean connect = bus.connect(cfg, new DefaultPortSelector());
            Assertions.assertTrue(connect);
            Logger.msg("Loop 1");
            try {
                SerialData s = bus.readS();
                Assertions.assertEquals(0, s.chk);
                bus.writeR(new SerialData(0, 7, 7, 7, 7, 7));
                s = bus.readS();
                Assertions.assertEquals(1, s.chk);
                bus.writeR(new SerialData(0, 1, 5, 78, -7, 11));
            } catch (SerialLongReadException | SerialWrongReadException | GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }

        }, "= Computer =");

        NativeEntry entry = new NativeEntry();
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup(IArdwConfig.BAUD_9600);
            Logger.msg("Loop 1");
            entry.loop();
            Logger.msg("Loop 2");
            entry.loop();
            Logger.msg("Finished");
        }, ARDUINO_THD);

        computerThd.start();
        arduinoThd.start();

        // << Z <<
        TestTimer.get().delayMs(99);
        Assertions.assertTrue(cableC2A.check().contains(Serial.Z_));
        cableC2A.input.clear();

        // >> J >>
        Assertions.assertTrue(cableA2C.check().startsWith(Serial.J_));
        cableA2C.releaseAll();

        // << JK <<
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.J_ + Serial.K, cableC2A.check());
        cableC2A.release(2);

        // >> K >>
        TestTimer.get().delayMs(99);
        Assertions.assertTrue(cableA2C.check().endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CTC1C1C1C999C <<
        TestTimer.get().delayMs(99);
        Assertions.assertEquals("CTC1C1C1C999C", cableC2A.check());
        cableC2A.release("CTC1C1C1C999C".length());

        // >> S >>
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.S + "000av+aw+ax+ay+az+" + T, cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.R_ + "av7+aw7+ax7+ay7+az7+" + T, cableC2A.check());
        cableC2A.releaseAll();

        // >> S >>
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.S + "001av+aw+ax+ay+az+" + T, cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.R + "av1+aw5+ax78+ay7-az11+" + T, cableC2A.check());
        cableC2A.releaseAll();

        TestTimer.get().delayMs(99);

        dump();
        Logger.msg("*** Finished ***");
    }

}

