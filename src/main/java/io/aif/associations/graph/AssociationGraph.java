package io.aif.associations.graph;


import io.aif.associations.calculators.edge.PredefinedEdgeWeightCalculator;
import io.aif.associations.calculators.vertex.ConnectionBasedWeightCalculator;
import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IGraph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssociationGraph<T> implements IGraph<IAssociationVertex<T>, IAssociationEdge> {

    private final Map<T, Map<T, Double>> graph;

    private final Map<T, IAssociationVertex<T>> cache = new HashMap<>();

    private AssociationGraph(final Map<T, Map<T, Double>> graph) {
        this.graph = graph;
        final PredefinedEdgeWeightCalculator<T> predefinedEdgeWeightCalculator = new PredefinedEdgeWeightCalculator<>(graph);
        final IVertexWeightCalculator<T> vertexWeightCalculator = new ConnectionBasedWeightCalculator<>(graph, predefinedEdgeWeightCalculator);
        graph.keySet().forEach(key -> cache.put(key, new AssociationVertex<T>(key, vertexWeightCalculator.calculate(key))));
    }

    private AssociationGraph() {
        this(new HashMap<>());
    }

    public static <T>AssociationGraph generateGraph(final Map<T, Map<T, Double>> graph) {
        return new AssociationGraph(graph);
    }

    public static <T>AssociationGraph generateGraph() {
        return new AssociationGraph();
    }

    @Override
    public Set<IAssociationVertex<T>> getVertex() {
        return new HashSet<>(cache.values());
    }

    @Override
    public Set<IAssociationVertex<T>> getNeighbors(final IAssociationVertex<T> vertex) {
        return graph.get(vertex).keySet().stream().map(cache::get).collect(Collectors.toSet());
    }

    @Override
    public Optional<IAssociationEdge> getEdge(final IAssociationVertex<T> from, final IAssociationVertex<T> to) {
        if (graph.get(from).keySet().contains(to))
            return Optional.of(new AssociationEdge(graph.get(from).get(to)));
        return Optional.empty();
    }

    private static class AssociationEdge implements IAssociationEdge {

        private final Double weight;

        public AssociationEdge(final Double weight) {
            this.weight = weight;
        }

        @Override
        public double weight() {
            return weight;
        }

    }

}
