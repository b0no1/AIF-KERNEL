package io.aif.graph.simple;


import io.aif.graph.normal.MapBasedGraphBuilder;
import io.aif.graph.simple.ISimpleGraphBuilder;

class MapBasedSimpleGraphBuilder<V> extends MapBasedGraphBuilder<V, Boolean> implements ISimpleGraphBuilder<V> {

    @Override
    public ISimpleGraph<V> build() {
        return new MapBasedSimpleGraph<>(g);
    }

}
