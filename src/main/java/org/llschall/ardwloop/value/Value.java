package org.llschall.ardwloop.value;

public class Value {

    public final int value;
    public final U u;
    public final V v;

    public Value(U u, V v, int value) {
        this.value = value;
        this.u = u;
        this.v = v;
    }

}
