package io.aif.associations.graph;


import io.aif.associations.calculators.edge.IEdgeWeightCalculator;
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

    private AssociationGraph(final Map<T, Map<T, Double>> graph,
                             final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.graph = graph;
        graph.keySet().forEach(key -> cache.put(key, new AssociationVertex<T>(key, vertexWeightCalculator.calculate(key))));
    }

    public static <T>AssociationGraph generateGraph(final Map<T, Map<T, Double>> graph, final IVertexWeightCalculator<T> vertexWeightCalculator) {
        return new AssociationGraph(graph, vertexWeightCalculator);
    }

    @Override
    public Set<IAssociationVertex<T>> getVertex() {
        return new HashSet<>(cache.values());
    }

    @Override
    public Set<IAssociationVertex<T>> getNeighbors(final IAssociationVertex<T> vertex) {
        final Map<T, Double> connection = graph.get(vertex);

        if (Objects.isNull(connection)) {
            return Collections.emptySet();
        }

        return connection.keySet().stream().map(cache::get).collect(Collectors.toSet());
    }

    @Override
    public Optional<IAssociationEdge> getEdge(final IAssociationVertex<T> from, final IAssociationVertex<T> to) {
        if (graph.get(from.item()) == null) return Optional.empty();

        if (graph.get(from.item()).keySet().contains(to.item()))
            return Optional.of(new AssociationEdge(graph.get(from.item()).get(to.item())));
        return Optional.empty();
    }

    @Override
    public boolean isEmpty() {
        return graph.isEmpty();
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
