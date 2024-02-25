package org.llschall.ardwloop;

import org.junit.jupiter.api.Assumptions;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalOnly {

    private final boolean isGit;
    private final static LocalOnly INSTANCE = new LocalOnly();

    private LocalOnly() {
        this.isGit = Files.exists(Paths.get("/home/runner"));
    }

    public static LocalOnly get() {
        return INSTANCE;
    }

    public void skipOnGit() {
        Assumptions.assumeFalse(isGit);
    }
}
