package io.aif.associations.graph;


import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IGraph;

import java.util.*;

public class AssociationGraph<T> implements IGraph<T> {

    private final Map<T, Map<T, Double>> graph;

    private final Map<T, Double> weights;

    private AssociationGraph(final Map<T, Map<T, Double>> graph,
                             final Map<T, Long> count,
                             final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.graph = graph;
        weights = vertexWeightCalculator.calculate(graph, count);
    }

    public static <T>AssociationGraph generateGraph(final Map<T, Map<T, Double>> graph,
                                                    final Map<T, Long> count ,
                                                    final IVertexWeightCalculator<T> vertexWeightCalculator) {
        return new AssociationGraph(graph, count, vertexWeightCalculator);
    }

    @Override
    public Set<T> getVertex() {
        return graph.keySet();
    }

    @Override
    public Set<T> getNeighbors(final T vertex) {
        return graph.get(vertex).keySet();
    }

    @Override
    public OptionalDouble getEdgeWeight(final T from, final T to) {
        if (!graph.get(from).containsKey(to)) return OptionalDouble.empty();
        return OptionalDouble.of(graph.get(from).get(to));
    }

    @Override
    public Double getVertexWeight(final T vertex) {
        return weights.get(vertex);
    }

    @Override
    public boolean isEmpty() {
        return graph.isEmpty();
    }

}
