package org.llschall.ardwloop;

import org.junit.jupiter.api.Assumptions;

public class LocalOnly {

    private final boolean isLocal;
    private final static LocalOnly INSTANCE = new LocalOnly();

    private LocalOnly() {
        this.isLocal = Boolean.getBoolean("test.jni");
    }

    public static LocalOnly get() {
        return INSTANCE;
    }

    public void skipOnGit() {
        Assumptions.assumeTrue(isLocal);
    }
}
