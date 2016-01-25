package io.aif.graph.normal;

import io.aif.graph.VertexNotFoundException;

import java.util.*;

public class MapBasedGraph<V, E> implements IGraph<V, E> {

    private final Map<V, Map<V, E>> g;

    public MapBasedGraph(final Map<V, Map<V, E>> g) {
        this.g = g;
    }

    @Override
    public Set<V> getVertices() {
        return g.keySet();
    }

    @Override
    public Set<V> getNeighbors(final V vertex) {
        if (!g.containsKey(vertex)) {
            Collections.emptySet();
        }
        if (g.get(vertex) == null) {
            return Collections.emptySet();
        }
        return g.get(vertex).keySet();
    }

    @Override
    public Optional<E> getEdge(final V from, final V to) {
        if (!g.containsKey(from) || ! g.containsKey(to)) {
            Collections.emptySet();
        }
        if (!g.get(from).containsKey(to)) {
            return Optional.empty();
        }
        return Optional.of(g.get(from).get(to));
    }

    @Override
    public boolean isEmpty() {
        return g.isEmpty();
    }

}
