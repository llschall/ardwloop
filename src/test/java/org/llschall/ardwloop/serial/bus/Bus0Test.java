package org.llschall.ardwloop.serial.bus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.JTestProgram;
import org.llschall.ardwloop.jni.BackEntry;
import org.llschall.ardwloop.jni.NativeEntry;
import org.llschall.ardwloop.motor.ProgramContainer;
import org.llschall.ardwloop.serial.Bus;
import org.llschall.ardwloop.serial.DefaultPortSelector;
import org.llschall.ardwloop.serial.ISerialMonitor;
import org.llschall.ardwloop.serial.Serial;
import org.llschall.ardwloop.serial.misc.FakeProvider;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.structure.data.ProgramCfg;
import org.llschall.ardwloop.structure.model.ArdwloopModel;
import org.llschall.ardwloop.structure.utils.Logger;
import org.llschall.ardwloop.structure.utils.Timer;

import java.util.concurrent.ArrayBlockingQueue;

public class Bus0Test extends AbstractBusTest {

    @BeforeEach
    void setUp() {
        StructureTimer.FAKE = true;
        BackEntry.setup(new BusEntry(this));
    }

    @AfterEach
    void close() {
        Logger.msg("*** Closing ***");
        BackEntry.close();
        Logger.msg("*** Closed ***");
    }

    @Test
    @Timeout(9)
    void testConnect() throws InterruptedException {

        // Arduino <<>> Computer

        ProgramCfg cfg = new ProgramCfg('T', 0, 0, 0);

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

        var finished = new ArrayBlockingQueue<String>(2);

        Thread computerThd = new Thread(() -> {
            Logger.msg("Start");
            bus.reset(cfg, new DefaultPortSelector());
            boolean connect = bus.connect();
            Assertions.assertTrue(connect);
            finished.add("Computer finished");
        }, COMPUTER_THD);

        NativeEntry entry = new NativeEntry();
        Thread arduinoThd = new Thread(() -> {
            Logger.msg("Start");
            entry.setup(IArdwConfig.BAUD_38400);
            Logger.msg("Finished");
            finished.add("Arduino finished");
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

        // << CTC20C9C9C0C0C0C <<
        Logger.msg("=== Step 5 ===");
        c2a = cableC2A.check("CTC20C9C9C0C0C0C".length());
        Assertions.assertEquals("CTC20C9C9C0C0C0C", c2a);

        Logger.msg(finished.take());
        Logger.msg(finished.take());

        dump();
        Logger.msg("Finished");
    }

}

