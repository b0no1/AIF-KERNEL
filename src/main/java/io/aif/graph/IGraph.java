package io.aif.graph;


import java.util.*;

public interface IGraph<V, E> {

    public Set<V> getVertices();

    public Set<V> getNeighbors(final V vertex) throws VertexNotFoundException;

    public Optional<E> getEdge(final V from, final V to) throws VertexNotFoundException;

    public boolean isEmpty();

}
