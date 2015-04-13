package io.aif.associations.graph;


import io.aif.associations.calculators.vertex.IVertexWeightCalculator;
import io.aif.associations.model.IAssociationEdge;
import io.aif.associations.model.IAssociationVertex;
import io.aif.associations.model.IGraph;

import java.util.*;
import java.util.stream.Collectors;

public class AssociationGraph<T> implements IGraph<IAssociationVertex<T>, Double> {

    private final IGraph<INodeWithCount<T>, Double> graph;

    private final Map<T, IAssociationVertex<T>> cache = new HashMap<>();

    private final Map<T, INodeWithCount<T>> mapping;

    private AssociationGraph(final IGraph<INodeWithCount<T>, Double> graph,
                             final IVertexWeightCalculator<T> vertexWeightCalculator) {
        this.graph = graph;
        final Map<T, Double> weights = vertexWeightCalculator.calculate(graph);
        graph.getVertex().forEach(key -> cache.put(key.item(), new AssociationVertex<T>(key.item(), weights.get(key.item()))));
        mapping = graph.getVertex().stream().collect(Collectors.toMap(k -> k.item(), v -> v));
    }

    public static <T>AssociationGraph generateGraph(final IGraph<INodeWithCount<T>, Double> graph, final IVertexWeightCalculator<T> vertexWeightCalculator) {
        return new AssociationGraph(graph, vertexWeightCalculator);
    }

    @Override
    public Set<IAssociationVertex<T>> getVertex() {
        return new HashSet<>(cache.values());
    }

    @Override
    public Set<IAssociationVertex<T>> getNeighbors(final IAssociationVertex<T> vertex) {
        final Set<INodeWithCount<T>> neighbors = graph.getNeighbors(mapping.get(vertex.item()));

        if (Objects.isNull(neighbors) || neighbors.isEmpty()) {
            return Collections.emptySet();
        }

        return neighbors.stream().map(cache::get).collect(Collectors.toSet());
    }

    @Override
    public Optional<Double> getEdge(final IAssociationVertex<T> from, final IAssociationVertex<T> to) {
        if (!graph.getNeighbors(mapping.get(from.item())).contains(mapping.get(to.item())))
            return Optional.empty();

        return graph.getEdge(mapping.get(from.item()), mapping.get(to.item()));
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
