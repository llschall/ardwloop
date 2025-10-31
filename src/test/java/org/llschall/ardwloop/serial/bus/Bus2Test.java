package org.llschall.ardwloop.serial.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.JTestProgram;
import org.llschall.ardwloop.SkipNext;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.Bus;
import org.llschall.ardwloop.serial.DefaultPortSelector;
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
import org.llschall.ardwloop.structure.data.SerialWrap;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.value.SerialData;

import static org.llschall.ardwloop.serial.Serial.T;

public class Bus2Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
        StructureTimer.FAKE = true;
        BackEntry.setup(new BusEntry(this));
    }

    @AfterEach
    void close() {
        Logger.msg("*** Closing ***");
        TestTimer.get().delayMs(999);
        BackEntry.close();
        Logger.msg("*** Closed ***");
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void testConnect() {

        // Arduino <<>> Computer

        ProgramCfg cfg = new ProgramCfg('T', 1, 999);

        ArdwloopModel model = new ArdwloopModel(
                new ProgramContainer(new JTestProgram()));
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
                SerialWrap s = bus.readS();
                Assertions.assertEquals(0, s.chk);
                bus.writeR(new SerialWrap(0, new SerialData(7, 0, 0, 0, 0)));
                s = bus.readS();
                Assertions.assertEquals(1, s.chk);
            } catch (SerialLongReadException | SerialWrongReadException |
                     GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }

        }, "= Computer =");


        Thread arduinoThd = new Thread(() -> {
            NativeEntry entry = new NativeEntry();
            Logger.msg("Start");
            entry.setup(IArdwConfig.BAUD_9600);
            Logger.msg("Loop");
            entry.importS("", 0, 'a', 0, 0, 0, 0, 0);
            entry.loop();
            Logger.msg("Finished");
        }, ARDUINO_THD);

        computerThd.start();
        arduinoThd.start();

        int ms = 99;

        // << Z <<
        Logger.msg("=== Step 0 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertTrue(cableC2A.check().contains(Serial.Z_));
        cableC2A.clear();

        if (SkipNext.get().skip()) return;

        // >> J >>
        Logger.msg("=== Step 1 ===");
        Assertions.assertTrue(cableA2C.check().startsWith(Serial.J_));
        cableA2C.releaseAll();

        // << JK <<
        Logger.msg("=== Step 2 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertEquals(Serial.J_ + Serial.K, cableC2A.check());
        cableC2A.release(2);

        // >> K >>
        Logger.msg("=== Step 3 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertTrue(cableA2C.check().endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CTC90C9C9C1C999C <<
        Logger.msg("=== Step 4 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertEquals("CTC90C9C9C1C999C", cableC2A.check());
        cableC2A.release("CTC90C9C9C1C999C".length());

        // >> S >>
        Logger.msg("=== Step 5 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertEquals(Serial.S + "000" + T, cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        Logger.msg("=== Step 6 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertEquals(Serial.R_ + "av7+" + T, cableC2A.check());
        cableC2A.releaseAll();

        // >> S >>
        Logger.msg("=== Step 7 ===");
        TestTimer.get().delayMs(ms);
        Assertions.assertEquals(Serial.S + "001" + T, cableA2C.check());
        cableA2C.releaseAll();

        Logger.msg("=== Step 8 ===");
        TestTimer.get().delayMs(ms);

        dump();
        Logger.msg("*** Finished ***");
    }

}

