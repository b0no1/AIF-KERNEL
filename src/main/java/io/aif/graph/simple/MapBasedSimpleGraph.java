package io.aif.graph.simple;


import io.aif.graph.normal.MapBasedGraph;

import java.util.Map;

class MapBasedSimpleGraph<V> extends MapBasedGraph<V, Boolean> implements ISimpleGraph<V> {

    MapBasedSimpleGraph(final Map<V, Map<V, Boolean>> g) {
        super(g);
    }

}
