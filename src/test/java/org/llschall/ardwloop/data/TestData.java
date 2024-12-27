package org.llschall.ardwloop.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.value.SerialData;

import java.util.Objects;

public class TestData {

    @Test
    public void testData() {

        Assertions.assertEquals(1, new SerialData(1).a.v);
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.w));

        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.w));

        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new SerialData(1, 0, 0, 0, 0).a.w));

    }
}
