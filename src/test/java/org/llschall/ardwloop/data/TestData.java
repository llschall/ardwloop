package org.llschall.ardwloop.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.structure.data.SerialData;
import org.llschall.ardwloop.structure.data.SerialVector;
import org.llschall.ardwloop.structure.data.SetupData;

public class TestData {

    @Test
    public void testData() {

        SerialData data = new SetupData(
                new SerialData(0, new SerialVector(0,0,0,0,0))
        ).getData();

        Assertions.assertEquals(0, data.a.v);
    }


}
