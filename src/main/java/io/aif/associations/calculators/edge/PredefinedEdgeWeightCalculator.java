package io.aif.associations.calculators.edge;


import edu.uci.ics.jung.graph.Graph;

public class PredefinedEdgeWeightCalculator<T> implements IEdgeWeightCalculator<T> {
    
    private final Graph<T, Double> connections;

    public PredefinedEdgeWeightCalculator(final Graph<T, Double> connections) {
        this.connections = connections;
    }

    @Override
    public double calculate(final T from, final T to) {
        return connections.findEdge(from, to);
    }
    
}
