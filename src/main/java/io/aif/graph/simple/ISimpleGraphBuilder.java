package io.aif.graph.simple;


import io.aif.graph.normal.IGraphBuilder;
import io.aif.graph.normal.MapBasedGraphBuilder;

public interface ISimpleGraphBuilder<V> extends IGraphBuilder<V, Boolean> {

    @Override
    public ISimpleGraph<V> build();

    default public void connect(final V from, final V to) {
        this.connect(from, to, true);
    }

    public static <V>ISimpleGraphBuilder<V> defaultBuilder() {
        return new MapBasedSimpleGraphBuilder<>();
    }

}
