package org.llschall.ardwloop.serial.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.llschall.ardwloop.JTestProgram;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.Bus;
import org.llschall.ardwloop.serial.Serial;
import org.llschall.ardwloop.serial.SerialLongReadException;
import org.llschall.ardwloop.serial.SerialWriteException;
import org.llschall.ardwloop.serial.SerialWrongReadException;
import org.llschall.ardwloop.serial.misc.FakeProvider;
import org.llschall.ardwloop.serial.misc.IArduino;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.serial.port.GotJException;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.model.Model;
import org.llschall.ardwloop.structure.utils.Logger;

public class BusTest1 extends AbstractBusTest {

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

        ProgramCfg cfg = new ProgramCfg('T', 1, sc);

        Model model = new Model(new ProgramContainer(new JTestProgram()));
        model.serialMdl.program.set(cfg);

        IArduino arduino = new Arduino(this);

        FakeProvider provider = new FakeProvider(arduino);
        Bus bus = new Bus(model, provider);

        Assertions.assertEquals("N/A", model.serialMdl.status.get());

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            boolean connect = bus.connect(cfg);
            Assertions.assertTrue(connect);
            Logger.msg("Loop 1");
            try {
                SerialData s = bus.readS();
                Assertions.assertEquals(0, s.chk);
                bus.writeR(new SerialData(null, 7, 7, 7, 7, 7));
                s = bus.readS();
                Assertions.assertEquals(1, s.chk);
                bus.writeR(new SerialData(null, 1, 5, 78, -7, 11));
            } catch (SerialLongReadException | SerialWrongReadException | GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }

        }, "= Computer =");

        NativeEntry entry = new NativeEntry(99, 1, 999, 1, 1);
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup();
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
        dump();
        Assertions.assertEquals(Serial.J_ + Serial.K, cableC2A.check());
        cableC2A.release(2);

        // >> K >>
        TestTimer.get().delayMs(99);
        Assertions.assertTrue(cableA2C.check().endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CT11 <<
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.C + "T11", cableC2A.check());
        cableC2A.release(4);

        // >> S >>
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(Serial.S + "000av+aw+ax+ay+az+", cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        TestTimer.get().delayMs(99);
        dump();
        Assertions.assertEquals(Serial.R_ + "av7+aw7+ax7+ay7+az7+", cableC2A.check());
        cableC2A.releaseAll();

        // >> S >>
        TestTimer.get().delayMs(99);
        dump();
        Assertions.assertEquals(Serial.S + "001av+aw+ax+ay+az+", cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        TestTimer.get().delayMs(99);
        dump();
        Assertions.assertEquals(Serial.R + "av1+aw5+ax78+ay7-az11+", cableC2A.check());
        cableC2A.releaseAll();

        TestTimer.get().delayMs(99);

        Logger.msg("*** Last ***");
        dump();
        Logger.msg("*** Finished ***");
    }

}

