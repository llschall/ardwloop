package org.llschall.ardwloop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocalOnlyTest {

    @Test
    public void localOnlyTest() {
        LocalOnly.get().skipOnGit();
        Assertions.assertEquals(2024, 1012*2);
    }

}
