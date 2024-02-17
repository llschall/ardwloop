package org.llschall.ardwloop;

import java.io.File;

public class LocalOnly {

    private final boolean localOnly;
    private final static LocalOnly INSTANCE = new LocalOnly();

    private LocalOnly() {
        this.localOnly = !new File("/etc/pulse").exists();
    }

    public static boolean get() {
        return INSTANCE.localOnly;
    }
}
