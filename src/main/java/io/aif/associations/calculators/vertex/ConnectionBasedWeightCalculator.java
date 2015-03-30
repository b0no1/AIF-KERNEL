package io.aif.associations.calculators.vertex;


import io.aif.associations.calculators.edge.IEdgeWeightCalculator;

import java.util.Map;
import java.util.OptionalDouble;

public class ConnectionBasedWeightCalculator<T> implements IVertexWeightCalculator<T> {

    // TODO extract this to config file
    private static  final double TARGET = .7;
    
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

        final OptionalDouble optAverageWeight = connections.get(vertex).keySet().stream()
                .mapToDouble(neighbor -> edgeWeightCalculator.calculate(vertex, neighbor))
                .average();
        if (!optAverageWeight.isPresent())
            return .0;
        return 1. - Math.abs(TARGET - optAverageWeight.getAsDouble());
    }
    
}
