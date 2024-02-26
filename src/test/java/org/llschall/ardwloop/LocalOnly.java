package org.llschall.ardwloop;

import org.junit.jupiter.api.Assumptions;

import java.io.File;

public class LocalOnly {

    private final boolean isGitHub;
    private final static LocalOnly INSTANCE = new LocalOnly();

    private LocalOnly() {
        this.isGitHub = new File("/home/runner").exists();
    }

    public static LocalOnly get() {
        return INSTANCE;
    }

    public void skipOnGitHub() {
        Assumptions.assumeFalse(isGitHub);
    }
}
