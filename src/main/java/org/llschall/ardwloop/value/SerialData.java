package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

/**
 * The class SerialData is used to store the data that is sent and received from the Arduino.
 * It wraps the data as vectors of 5 integers.
 */
public class SerialData {

    public String str = "";

    /**
     * The vector a that wraps 5 integers
     */
    public final V a = new V();
    /**
     * The vector b that wraps 5 integers
     */
    public final V b = new V();

    /**
     * The vector c that wraps 5 integers
     */
    public final V c = new V();
    /**
     * The vector d that wraps 5 integers
     */
    public final V d = new V();
    /**
     * The vector e that wraps 5 integers
     */
    public final V e = new V();
    /**
     * The vector f that wraps 5 integers
     */
    public final V f = new V();
    /**
     * The vector g that wraps 5 integers
     */
    public final V g = new V();
    /**
     * The vector h that wraps 5 integers
     */
    public final V h = new V();
    /**
     * The vector i that wraps 5 integers
     */
    public final V i = new V();

    /**
     * Constructor that creates an empty SerialData
     */
    public SerialData() {
    }

    public SerialData(int av) {
        a.v = av;
    }

    public SerialData(int av, int aw) {
        a.v = av;
        a.w = aw;
    }

    public SerialData(String str, int av, int aw, int ax, int ay, int az) {
        this.str = str;
        a.v = av;
        a.w = aw;
        a.x = ax;
        a.y = ay;
        a.z = az;
    }

    public SerialData(int av, int aw, int ax, int ay, int az) {
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
