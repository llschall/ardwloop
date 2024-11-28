package org.llschall.ardwloop.value;

import java.util.HashMap;
import java.util.Map;

public class ValueMap {

    public final Map<U, Map<V, Integer>> map;

    public ValueMap() {
        this.map = new HashMap<>();
        for (U u : U.values()) {
            Map<V, Integer> map = new HashMap<>();
            this.map.put(u, map);
            for (V v : V.values()) {
                map.put(v, 0);
            }
        }
    }
}
