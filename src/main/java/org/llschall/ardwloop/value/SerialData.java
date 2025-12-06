package org.llschall.ardwloop.value;

import org.llschall.ardwloop.structure.StructureException;

/**
 * The class SerialData is used to store the data that is sent and received from the Arduino.
 * It wraps the data as vectors of 5 integers.
 */
public class SerialData {

    /**
     * String data associated with this SerialData instance.
     */
    public String str = "";

    /**
     * The inner class V represents a vector of 5 integers.
     */
    public Integer[] array;

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

    /**
     * Constructor that initializes vector a's v value.
     *
     * @param av value for a.v
     */
    public SerialData(int av) {
        a.v = av;
    }

    /**
     * Constructor that initializes vector a's v and w values.
     *
     * @param av value for a.v
     * @param aw value for a.w
     */
    public SerialData(int av, int aw) {
        a.v = av;
        a.w = aw;
    }

    /**
     * Constructor that initializes the string and all values of vector a.
     *
     * @param str string value
     * @param av  value for a.v
     * @param aw  value for a.w
     * @param ax  value for a.x
     * @param ay  value for a.y
     * @param az  value for a.z
     */
    public SerialData(String str, int av, int aw, int ax, int ay, int az) {
        this.str = str;
        a.v = av;
        a.w = aw;
        a.x = ax;
        a.y = ay;
        a.z = az;
    }

    /**
     * Constructor that initializes the array and all values of vector a.
     *
     * @param array array of Integer
     * @param av    value for a.v
     * @param aw    value for a.w
     * @param ax    value for a.x
     * @param ay    value for a.y
     * @param az    value for a.z
     */
    public SerialData(Integer[] array, int av, int aw, int ax, int ay, int az) {
        this.array = array;
        a.v = av;
        a.w = aw;
        a.x = ax;
        a.y = ay;
        a.z = az;
    }

    /**
     * Constructor that initializes all values of vector a.
     *
     * @param av value for a.v
     * @param aw value for a.w
     * @param ax value for a.x
     * @param ay value for a.y
     * @param az value for a.z
     */
    public SerialData(int av, int aw, int ax, int ay, int az) {
        a.v = av;
        a.w = aw;
        a.x = ax;
        a.y = ay;
        a.z = az;
    }

    /**
     * Returns the vector corresponding to the given character.
     *
     * @param c character representing the vector ('a' to 'i')
     * @return the corresponding V instance
     * @throws StructureException if the character is not valid
     */
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
