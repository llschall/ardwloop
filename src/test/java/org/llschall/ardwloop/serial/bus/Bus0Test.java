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
import org.llschall.ardwloop.serial.Bus;
import org.llschall.ardwloop.serial.DefaultPortSelector;
import org.llschall.ardwloop.serial.ISerialMonitor;
import org.llschall.ardwloop.serial.Serial;
import org.llschall.ardwloop.serial.SerialLongReadException;
import org.llschall.ardwloop.serial.SerialWriteException;
import org.llschall.ardwloop.serial.SerialWrongReadException;
import org.llschall.ardwloop.serial.misc.FakeProvider;
import org.llschall.ardwloop.serial.misc.TestTimer;
import org.llschall.ardwloop.serial.port.GotJException;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.data.SerialWrap;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.structure.utils.Timer;
import org.llschall.ardwloop.value.SerialData;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.llschall.ardwloop.serial.Serial.R;
import static org.llschall.ardwloop.serial.Serial.S;
import static org.llschall.ardwloop.serial.Serial.T;

public class Bus0Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
        StructureTimer.FAKE = true;
        BackEntry.setup(new BusEntry(this));
    }

    @AfterEach
    void close() {
        Logger.msg("*** Closing ***");
        TestTimer.get().delayMs(888);
        BackEntry.close();
        Logger.msg("*** Closed ***");
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void testConnect(int sc) {

        // Arduino <<>> Computer

        ProgramCfg cfg = new ProgramCfg('T', 0, 0);

        ArdwloopModel model = new ArdwloopModel(
                new ProgramContainer(new JTestProgram()));
        model.serialMdl.program.set(cfg);
        model.serialMdl.resetPin.set(20);

        Arduino arduino = new Arduino(this);

        FakeProvider provider = new FakeProvider(arduino);

        var monitor = new ISerialMonitor() {
            @Override
            public void fireZSent() {
                Logger.msg("Z sent.");
            }
        };

        Bus bus = new Bus(model, provider, new Timer(), monitor);

        Assertions.assertEquals("N/A", model.serialMdl.status.get());

        var finishedA = new AtomicBoolean(false);
        var finishedC = new AtomicBoolean(false);

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            boolean connect = bus.connect(cfg, new DefaultPortSelector());

            Assertions.assertTrue(connect);
            Logger.msg("Loop 1");
            try {
                SerialWrap s = bus.readS();
                Assertions.assertEquals(0, s.chk);
                bus.writeR(new SerialWrap(0, new SerialData(7, 7, 7, 7, 7)));
                finishedC.set(true);
            } catch (SerialLongReadException | SerialWrongReadException |
                     GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }
        }, COMPUTER_THD);

        NativeEntry entry = new NativeEntry();
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup(IArdwConfig.BAUD_38400);
            Logger.msg("Finished");
            finishedA.set(true);
        }, ARDUINO_THD);

        computerThd.start();

        // << Z <<
        Logger.msg("=== Step 0 ===");
        String c2a = cableC2A.check(1);
        Assertions.assertEquals(Serial.Z_, c2a);

        arduinoThd.start();
        cableC2A.pushZero();

        // >> J >>
        Logger.msg("=== Step 1 ===");
        String a2c = cableA2C.check(1);
        Assertions.assertEquals(Serial.J_, a2c);

        // unlock ignore all next
        cableA2C.pushZero();

        Logger.msg("=== Step 2a ===");
        // >> J >>
        cableC2A.pushZero();
        a2c = cableA2C.check(1);
        Assertions.assertEquals(Serial.J_, a2c);

        // << J <<
        c2a = cableC2A.check(1);
        Logger.msg("=== Step 2b === ");
        Assertions.assertEquals(Serial.J_, c2a);

        // << K <<
        Logger.msg("=== Step 3 ===");
        cableA2C.pushZero();
        cableA2C.pushZero();
        cableA2C.pushZero();
        cableA2C.pushZero();
        c2a = cableC2A.check(1);
        Assertions.assertEquals(Serial.K_, c2a);

        // >> K >>
        Logger.msg("=== Step 4 ===");
        cableC2A.pushZero();
        a2c = cableA2C.check(1);
        Assertions.assertEquals(Serial.K_, a2c);

        // << CTC20C9C9C0C0C <<
        Logger.msg("=== Step 5 ===");
        c2a = cableC2A.check("CTC20C9C9C0C0C".length());
        Assertions.assertEquals("CTC20C9C9C0C0C", c2a);

        // >> S >>
        Logger.msg("=== Step 6 ===");
        a2c = cableA2C.check(5);
        Assertions.assertEquals(S + "000" + T, a2c);

        // << R <<
        Logger.msg("=== Step 7 ===");
        c2a = cableC2A.check(22);
        Assertions.assertEquals(R + "av7+aw7+ax7+ay7+az7+" + T, c2a);

        TestTimer.get().delayMs(88);

        Assertions.assertTrue(finishedC.get());
        Assertions.assertTrue(finishedA.get());

        dump();
        Logger.msg("Finished");
    }

}

