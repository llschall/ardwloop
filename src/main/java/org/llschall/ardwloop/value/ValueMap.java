package org.llschall.ardwloop.value;

import java.util.HashMap;
import java.util.Map;

public class ValueMap {

    public final Map<U, Map<V, Integer>> map;

    public final Map<V, Integer> a;
    public final Map<V, Integer> b;

    public ValueMap() {
        this.map = new HashMap<>();
        for (U u : U.values()) {
            Map<V, Integer> map = new HashMap<>();
            this.map.put(u, map);
            for (V v : V.values()) {
                map.put(v, 0);
            }
        }
        a = map.get(U.a);
        b = map.get(U.b);
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
