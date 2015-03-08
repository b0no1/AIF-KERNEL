package io.aif.associations.calculators.vertex;


import edu.uci.ics.jung.graph.Graph;
import io.aif.associations.calculators.edge.IEdgeWeightCalculator;

import java.util.Set;
import java.util.stream.Collectors;

public class ConnectionBasedWeightCalculator<T> implements IVertexWeightCalculator<T> {

    // TODO extract this to config file
    private static  final double TARGET = .7;
    
    private final Graph<T, Double> connections;
    
    private final IEdgeWeightCalculator<T> edgeWeightCalculator;

    public ConnectionBasedWeightCalculator(final Graph<T, Double> connections, 
                                           final IEdgeWeightCalculator<T> edgeWeightCalculator) {
        this.connections = connections;
        this.edgeWeightCalculator = edgeWeightCalculator;
    }

    @Override
    public double calculate(final T vertex) {
        final Double averageWeight = connections.getNeighbors(vertex).stream()
                .map(neighbor -> edgeWeightCalculator.calculate(vertex, neighbor))
                .collect(Collectors.summarizingDouble(x -> x))
                .getAverage();
        return 1. - Math.abs(TARGET - averageWeight);
    }
    
}
