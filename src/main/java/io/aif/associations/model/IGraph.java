package io.aif.associations.model;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IGraph<V> {

    public Set<V> getVertex();

    public Set<V> getNeighbors(final V vertex);

    public OptionalDouble getEdgeWeight(final V from, final V to);

    public Double getVertexWeight(final V vertex);

    public boolean isEmpty();

}
