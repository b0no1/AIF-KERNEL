package io.aif.associations.model;


import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IGraph<V, E> {

    public Set<V> getVertex();

    public Set<V> getNeighbors(final V vertex);

    public Optional<E> getEdge(final V from, final V to);

}
