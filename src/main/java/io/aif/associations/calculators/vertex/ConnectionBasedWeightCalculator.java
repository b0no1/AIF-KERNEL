package io.aif.associations.calculators.vertex;


import java.util.*;
import java.util.stream.Collectors;

public class ConnectionBasedWeightCalculator<T> implements IVertexWeightCalculator<T> {

    @Override
    public Map<T, Double> calculate(final Map<T, Map<T, Double>>  graph, final Map<T, Long> count) {
        final Map<T, Double> weights = graph.keySet().stream().collect(Collectors.toMap(k -> k, k -> calculate(k, graph, count)));
        final Optional<Double> optMax = weights.values().stream().max(Double::compare);
        if (!optMax.isPresent()) return Collections.emptyMap();
        if (optMax.get() == .0) {
           return weights.keySet().stream().collect(Collectors.toMap(k -> k, k -> .0));
        }
        return weights.keySet().stream().collect(Collectors.toMap(k -> k, k -> weights.get(k) / optMax.get()));
    }

    private double calculate(final T vertex, final Map<T, Map<T, Double>> graph, final Map<T, Long> count) {
        if (graph.get(vertex).keySet().isEmpty()) return .0;

        final OptionalDouble optAverageWeight = graph.get(vertex).keySet().stream()
                .mapToDouble(neighbor -> graph.get(vertex).get(neighbor))
                .max();
        if (!optAverageWeight.isPresent())
            return .0;
        return (optAverageWeight.getAsDouble()) / ((double)graph.get(vertex).keySet().size() * (double)count.get(vertex));
    }
    
}
