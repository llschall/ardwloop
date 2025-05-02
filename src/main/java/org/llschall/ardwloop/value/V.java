package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

/**
 * The V class represents a structure with five integer values: v, w, x, y, and z.
 */
public class V {

    /**
     * The integer value of v.
     */
    public int v;

    /**
     * The integer value of w.
     */
    public int w;

    /**
     * The integer value of x.
     */
    public int x;

    /**
     * The integer value of y.
     */
    public int y;

    /**
     * The integer value of z.
     */
    public int z;

    public void fromChar(char c, int i) {
        switch (c) {
            case 'v' -> v = i;
            case 'w' -> w = i;
            case 'x' -> x = i;
            case 'y' -> y = i;
            case 'z' -> z = i;
            default -> throw new StructureException("Unexpected character: " + c);
        }
    }
}
