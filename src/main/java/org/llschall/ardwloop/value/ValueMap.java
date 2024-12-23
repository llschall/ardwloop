package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

public class ValueMap {

    public final V a = new V();
    public final V b = new V();
    public final V c = new V();
    public final V d = new V();
    public final V e = new V();
    public final V f = new V();
    public final V g = new V();
    public final V h = new V();
    public final V i = new V();

    public ValueMap() {
    }

    public ValueMap(int av) {
        a.v = av;
    }

    public ValueMap(int av, int aw) {
        a.v = av;
        a.w = aw;
    }

    public ValueMap(int av, int aw, int ax, int ay, int az) {
        a.v = av;
        a.w = aw;
        a.x = ax;
        a.y = ay;
        a.z = az;
    }

    public V fromChar(char c) {
        if (c == 'a')
            return a;
        if (c == 'b')
            return b;
        if (c == 'c')
            return this.c;
        if (c == 'd')
            return d;
        if (c == 'e')
            return e;
        if (c == 'f')
            return f;
        if (c == 'g')
            return g;
        if (c == 'h')
            return h;
        if (c == 'i')
            return i;

        throw new StructureException("Unexpected char: " + c);
    }

}
