package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

public class V {

    public int v;
    public int w;
    public int x;
    public int y;
    public int z;

    public void fromChar(char c, int i) {
        switch (c) {
            case 'v' -> v=i;
            case 'w' -> w=i;
            case 'x' -> x=i;
            case 'y' -> y=i;
            case 'z' -> z=i;
            default -> throw new StructureException("Unexpected character: " + c);
        }
    }
}
