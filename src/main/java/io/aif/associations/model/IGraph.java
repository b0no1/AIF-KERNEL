package io.aif.associations.model;


import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IGraph<V, E> {

    public Set<V> getVertex();

    public Set<V> getNeighbors(final V vertex);

    public Optional<E> getEdge(final V from, final V to);

    public boolean isEmpty();

}
