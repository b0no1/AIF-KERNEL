package io.aif.associations.builder;


import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.graph.normal.IGraph;
import io.aif.graph.normal.IGraphBuilder;
import io.aif.graph.VertexNotFoundException;

import java.util.*;

public class AssociationGraph<T> implements IGraph<T, Double> {

    private final IGraph<T, Double> graph;

    private final Map<T, Double> weights;

    private AssociationGraph(final Map<T, Map<T, Double>> graph,
                             final Map<T, Long> count,
                             final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.graph = IGraphBuilder.build(graph);
        weights = vertexWeightCalculator.calculate(graph, count);
    }

    public static <T>AssociationGraph generateGraph(final Map<T, Map<T, Double>> graph,
                                                    final Map<T, Long> count ,
                                                    final IVertexWeightCalculator<T> vertexWeightCalculator) {
        return new AssociationGraph(graph, count, vertexWeightCalculator);
    }

    @Override
    public Set<T> getVertices() {
        return graph.getVertices();
    }

    @Override
    public Set<T> getNeighbors(final T vertex) throws VertexNotFoundException {
        return graph.getNeighbors(vertex);
    }

    @Override
    public Optional<Double> getEdge(final T from, final T to) throws VertexNotFoundException {
        return graph.getEdge(from, to);
    }

    public Double getVertexWeight(final T vertex) {
        return weights.get(vertex);
    }

    @Override
    public boolean isEmpty() {
        return graph.isEmpty();
    }

}
