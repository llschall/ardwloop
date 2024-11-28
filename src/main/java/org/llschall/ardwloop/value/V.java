package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

public enum V {
    v, w, x, y, z;

    public static V fromChar(char c) {
        switch (c) {
            case 'v' -> {
                return V.v;
            }
            case 'w' -> {
                return V.w;
            }
            case 'x' -> {
                return V.x;
            }
            case 'y' -> {
                return V.y;
            }
            case 'z' -> {
                return V.z;
            }
            default -> throw new StructureException("Unexpected character: " + c);
        }
    }
}
