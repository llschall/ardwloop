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
                cableC2A.latch.countDown();
                Logger.msg("Latch released.");
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
            } catch (SerialLongReadException | SerialWrongReadException | GotJException | SerialWriteException e) {
                throw new RuntimeException(e);
            }
        }, COMPUTER_THD);

        NativeEntry entry = new NativeEntry();
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            cableA2C.latch.countDown();
            entry.setup(IArdwConfig.BAUD_38400);
            Logger.msg("Finished");
            finishedA.set(true);
        }, ARDUINO_THD);

        computerThd.start();

        // << Z <<
        Logger.msg("=== Step 0 ===");
        try {
            Logger.msg("Waiting for latch");
            cableC2A.latch.await();
            Logger.msg("Latch released");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String c2a = cableC2A.check();
        Assertions.assertTrue(c2a.contains(Serial.Z_));
        cableC2A.input.clear();

        arduinoThd.start();
        // >> J >>
        Logger.msg("=== Step 1 ===");
        try {
            Logger.msg("Waiting for latch");
            cableA2C.latch.await();
            Logger.msg("Latch released");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Logger.msg("=== Step 1a ===");
        TestTimer.get().delayMs(88);
        String a2c = cableA2C.check();
        Logger.msg("=== Step 1b === " + a2c);
        Assertions.assertTrue(a2c.startsWith(Serial.J_));
        Logger.msg("=== Step 1c ===");
        cableA2C.releaseAll();
        Logger.msg("=== Step 1d ===" + cableA2C.check());

        // << J <<
        Logger.msg("=== Step 2 ===");
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Logger.msg(c2a);
        Assertions.assertTrue(c2a.startsWith(Serial.J_));
        cableC2A.release(1);

        // << K <<
        Logger.msg("=== Step 3 ===");
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Assertions.assertEquals(Serial.K_, c2a);
        cableC2A.release(1);

        // >> K >>
        Logger.msg("=== Step 4 ===");
        TestTimer.get().delayMs(88);
        a2c = cableA2C.check();
        Assertions.assertTrue(a2c.endsWith(Serial.K_));
        cableA2C.releaseAll();

        // << CTC20C9C9C0C0C <<
        Logger.msg("=== Step 5 ===");
        TestTimer.get().delayMs(88);
        c2a = cableC2A.check();
        Assertions.assertEquals("CTC20C9C9C0C0C", c2a);
        cableC2A.release("CTC20C9C9C0C0C".length());

        // >> S >>
        Logger.msg("=== Step 6 ===");
        TestTimer.get().delayMs(99);
        Assertions.assertEquals(S + "000" + T, cableA2C.check());
        cableA2C.releaseAll();

        // << R <<
        Logger.msg("=== Step 7 ===");
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

