package org.llschall.ardwloop.value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueMap {

    public final Map<V, Integer> a = new HashMap<>();
    public final Map<V, Integer> b = new HashMap<>();
    public final Map<V, Integer> c = new HashMap<>();
    public final Map<V, Integer> d = new HashMap<>();
    public final Map<V, Integer> e = new HashMap<>();
    public final Map<V, Integer> f = new HashMap<>();
    public final Map<V, Integer> g = new HashMap<>();
    public final Map<V, Integer> h = new HashMap<>();
    public final Map<V, Integer> i = new HashMap<>();

    public final List<Map<V, Integer>> entries = List.of(
            a, b, c, d, e, f, g, h
    );

    public ValueMap() {

        for (Map<V, Integer> map : entries) {
            for (V v : V.values()) {
                map.put(v, 0);
            }
        }
    }

    public ValueMap(int av) {
        this();
        a.put(V.v, av);
    }

    public ValueMap(int av, int aw) {
        this();
        a.put(V.v, av);
        a.put(V.w, aw);
    }

    public ValueMap(int av, int aw, int ax, int ay, int az) {
        this();
        a.put(V.v, av);
        a.put(V.w, aw);
        a.put(V.x, ax);
        a.put(V.y, ay);
        a.put(V.z, az);
    }

}
