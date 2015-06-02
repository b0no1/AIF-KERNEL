package io.aif.graph;

import java.util.Map;

public interface IGraphBuilder<V, E> {

    public void add(final V vertex);

    public IGraph<V, E> build();

    public void connect(final V from, final V to, final E edge);

    public static <V, E>IGraphBuilder<V, E> defaultBuilder() {
        return new MapBasedGraphBuilder<>();
    }

    public static <V, E>IGraph<V, E> build(final Map<V, Map<V, E>> g) {
        return new MapBasedGraph<>(g);
    }

}
