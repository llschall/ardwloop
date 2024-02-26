package org.llschall.ardwloop;

import org.junit.jupiter.api.Assumptions;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalOnly {

    private final boolean isLocal;
    private final static LocalOnly INSTANCE = new LocalOnly();

    private LocalOnly() {
        this.isLocal = Files.exists(Paths.get("local.txt"));
    }

    public static LocalOnly get() {
        return INSTANCE;
    }

    public void skipOnGit() {
        Assumptions.assumeTrue(isLocal);
    }
}
