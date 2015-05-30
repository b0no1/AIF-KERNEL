package io.aif.graph;

import java.util.*;

class MapBasedGraph<V, E> implements IGraph<V, E> {

    private final Map<V, Map<V, E>> g;

    public MapBasedGraph(final Map<V, Map<V, E>> g) {
        this.g = g;
    }

    @Override
    public Set<V> getVertices() {
        return g.keySet();
    }

    @Override
    public Set<V> getNeighbors(final V vertex) throws VertexNotFoundException {
        if (!g.containsKey(vertex)) {
            throw new VertexNotFoundException();
        }
        return g.get(vertex).keySet();
    }

    @Override
    public Optional<E> getEdge(final V from, final V to) throws VertexNotFoundException {
        if (!g.containsKey(from) || ! g.containsKey(to)) {
            throw new VertexNotFoundException();
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
