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

    /**
     * Sets the value of the specified field (v, w, x, y, or z) to the given integer.
     *
     * @param c the character representing the field ('v', 'w', 'x', 'y', or 'z')
     * @param i the integer value to set
     * @throws StructureException if the character is not one of the valid fields
     */
    public void fromChar(char c, int i) {
        switch (c) {
            case 'v' -> v = i;
            case 'w' -> w = i;
            case 'x' -> x = i;
            case 'y' -> y = i;
            case 'z' -> z = i;
            default ->
                    throw new StructureException("Unexpected character: " + c);
        }
    }
}
