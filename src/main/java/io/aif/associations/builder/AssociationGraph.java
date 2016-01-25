package io.aif.associations.builder;


import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.fuzzy.bool.FuzzyBoolean;
import io.aif.fuzzy.graph.IFuzzyGraph;
import io.aif.graph.normal.IGraph;
import io.aif.graph.normal.IGraphBuilder;
import io.aif.graph.VertexNotFoundException;

import java.util.*;

public class AssociationGraph<T> implements IFuzzyGraph<T> {

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
    public Set<T> getNeighbors(final T vertex) {
        return graph.getNeighbors(vertex);
    }

    @Override
    public Optional<FuzzyBoolean> getEdge(final T from, final T to) {
        // TODO there should be a way to use non default thershold for the FuzzyBoolean here
        return graph.getEdge(from, to).map(FuzzyBoolean::new);
    }

    public Double getVertexWeight(final T vertex) {
        return weights.get(vertex);
    }

    @Override
    public boolean isEmpty() {
        return graph.isEmpty();
    }

}
