package io.aif.associations.calculators.edge;


import java.util.Map;

public class PredefinedEdgeWeightCalculator<T> implements IEdgeWeightCalculator<T> {
    
    private final Map<T, Map<T, Double>> connections;

    public PredefinedEdgeWeightCalculator(final Map<T, Map<T, Double>> connections) {
        this.connections = connections;
    }

    @Override
    public double calculate(final T from, final T to) {
        return connections.get(from).get(to);
    }
    
}
