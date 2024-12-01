package org.llschall.ardwloop.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.llschall.ardwloop.value.V;
import org.llschall.ardwloop.value.ValueMap;

import java.util.Objects;

public class TestData {

    @Test
    public void testData() {

        Assertions.assertEquals(1, new ValueMap(1).a.v);
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.w));

        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.w));

        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.v));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 1).a.w));
        Assertions.assertEquals(1, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.v));
        Assertions.assertEquals(0, Objects.requireNonNull(new ValueMap(1, 0, 0, 0, 0).a.w));

    }
}
