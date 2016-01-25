package io.aif.graph.normal;


import io.aif.graph.VertexNotFoundException;

import java.util.*;

public interface IGraph<V, E> {

    public Set<V> getVertices();

    public Set<V> getNeighbors(final V vertex);

    public Optional<E> getEdge(final V from, final V to);

    public boolean isEmpty();

}
