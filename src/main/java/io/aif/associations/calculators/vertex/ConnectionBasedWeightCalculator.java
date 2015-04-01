package io.aif.associations.calculators.vertex;


import io.aif.associations.calculators.edge.IEdgeWeightCalculator;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

public class ConnectionBasedWeightCalculator<T> implements IVertexWeightCalculator<T> {
    
    private final Map<T, Map<T, Double>> connections;
    
    private final IEdgeWeightCalculator<T> edgeWeightCalculator;

    public ConnectionBasedWeightCalculator(final Map<T, Map<T, Double>> connections,
                                           final IEdgeWeightCalculator<T> edgeWeightCalculator) {
        this.connections = connections;
        this.edgeWeightCalculator = edgeWeightCalculator;
    }

    @Override
    public double calculate(final T vertex) {
        if (!connections.containsKey(vertex)) return .0;

        if (connections.get(vertex).keySet().size() <= 2) return .0;

        final OptionalDouble optAverageWeight = connections.get(vertex).keySet().stream()
                .mapToDouble(neighbor -> edgeWeightCalculator.calculate(vertex, neighbor))
                .average();
        if (!optAverageWeight.isPresent())
            return .0;
        return (/*Math.sqrt(((double)count.get(vertex)) / (double)max) */ optAverageWeight.getAsDouble()) / (double)connections.get(vertex).size();
//        final Optional<Double> maxWeightOpt = connections.get(vertex).values().stream().max(Double::compare);
//        final Optional<Double> minWeightOpt = connections.get(vertex).values().stream().min(Double::compare);
//
//        if (!maxWeightOpt.isPresent() || !minWeightOpt.isPresent()) return .0;
//
//        final double delta = maxWeightOpt.get() - minWeightOpt.get();
//
//        return(((double)count.get(vertex) / (double)max) * delta) / connections.get(vertex).size();
    }
    
}
