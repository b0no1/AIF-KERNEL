package io.aif.associations.calculators.vertex;


import io.aif.associations.graph.INodeWithCount;
import io.aif.associations.model.IGraph;

import java.util.*;
import java.util.stream.Collectors;

public class ConnectionBasedWeightCalculator<T> implements IVertexWeightCalculator<T> {

    @Override
    public Map<T, Double> calculate(final IGraph<INodeWithCount<T>, Double> graph) {
        final Map<T, Double> weights = graph.getVertex().stream().collect(Collectors.toMap(k -> k.item(), k -> calculate(k, graph)));
        final Optional<Double> optMax = weights.values().stream().max(Double::compare);
        if (!optMax.isPresent()) return Collections.emptyMap();
        return weights.keySet().stream().collect(Collectors.toMap(k -> k, k -> weights.get(k) / optMax.get()));
    }

    private double calculate(final INodeWithCount<T> vertex, final IGraph<INodeWithCount<T>, Double> graph) {
        if (!graph.getNeighbors(vertex).isEmpty()) return .0;

        if (graph.getNeighbors(vertex).size() <= 2) return .0;

        final OptionalDouble optAverageWeight = graph.getNeighbors(vertex).stream()
                .mapToDouble(neighbor -> graph.getEdge(vertex, neighbor).get())
                .max();
        if (!optAverageWeight.isPresent())
            return .0;
        return (optAverageWeight.getAsDouble()) / ((double)graph.getNeighbors(vertex).size() * (double)vertex.count());
    }
    
}
