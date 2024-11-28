package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

public enum U {
    a, b, c, d, e, f, g, h, i;

    public static U fromChar(char c) {
        switch (c) {
            case 'a' -> {
                return a;
            }
            case 'b' -> {
                return b;
            }
            case 'c' -> {
                return U.c;
            }
            case 'd' -> {
                return U.d;
            }
            case 'e' -> {
                return U.e;
            }
            case 'f' -> {
                return U.f;
            }
            case 'g' -> {
                return U.g;
            }
            case 'h' -> {
                return U.h;
            }
            case 'i' -> {
                return U.i;
            }
            default -> throw new StructureException("Unexpected character: " + c);
        }

    }

}
